package com.bancodedados.demo.DAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bancodedados.demo.Model.Aluno;
import com.bancodedados.demo.Model.Professor;
import com.bancodedados.demo.Model.Usuario;

@Repository 
public class UsuarioDAO {
    private final JdbcTemplate jdbcTemplate;

    public UsuarioDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // esse aqui ele já vem com a senha
    private final RowMapper<Usuario> rowMapperLogin = (rs, rowNum) -> {
        String tipo = rs.getString("tipo");
        Usuario usuario;
        if(tipo.equals("professor")){
            usuario = new Professor();
        }
        else{
            usuario = new Aluno();
        }
        usuario.setId(rs.getLong("id"));
        usuario.setNome(rs.getString("nome"));
        usuario.setEmail(rs.getString("email"));
        usuario.setSenha(rs.getString("senha"));
        return usuario;
    };

    private final RowMapper<Usuario> rowMapper = (rs, rowNum) -> {
        String tipo = rs.getString("tipo");
        Usuario usuario;
        if(tipo.equals("professor")){
            usuario = new Professor();
        }
        else{
            usuario = new Aluno();
        }
        usuario.setId(rs.getLong("id"));
        usuario.setNome(rs.getString("nome"));
        usuario.setEmail(rs.getString("email"));
        return usuario;
    };

    @Transactional
    public Long save(Usuario usuario) {
        String query = "INSERT INTO usuario (nome, email, senha) VALUES (?, ?, ?) RETURNING id";
        Long id = jdbcTemplate.queryForObject(query, Long.class, usuario.getNome(), usuario.getEmail(), usuario.getSenha());

        if (usuario instanceof Professor) {
            jdbcTemplate.update("INSERT INTO professor (id) VALUES (?)", id);
        } 
        else if (usuario instanceof Aluno) {
            jdbcTemplate.update("INSERT INTO aluno (id) VALUES (?)", id);
        }

        return id;
    }

    public Usuario findByEmail(String email){
        String query = "SELECT u.id, u.nome, u.email, u.senha, " +
                            "CASE " +
                                "WHEN p.id IS NOT NULL THEN 'professor' " +
                                "WHEN a.id IS NOT NULL THEN 'aluno' " +
                            "END AS tipo " +
                        "FROM usuario u LEFT JOIN professor p on u.id = p.id " +
                        "LEFT JOIN aluno a on u.id = a.id WHERE u.email = ?";
        List<Usuario> lista = new ArrayList<>();
        lista = jdbcTemplate.query(query, rowMapperLogin, email);

        // se der tempo de tratar para 2 pessoas não usarem o mesmo email, tirar essa gambiarra aqui
        if(lista.size() <= 0){
            return null;
        }
        return lista.get(0);
    }

    public Usuario findById(Long id){
        String query = "SELECT u.id, u.nome, u.email, " +
                            "CASE " +
                                "WHEN p.id IS NOT NULL THEN 'professor' " +
                                "WHEN a.id IS NOT NULL THEN 'aluno' " +
                            "END AS tipo " +
                        "FROM usuario u LEFT JOIN professor p on u.id = p.id " +
                        "LEFT JOIN aluno a on u.id = a.id WHERE u.id = ?";
        List<Usuario> lista = new ArrayList<>();
        lista = jdbcTemplate.query(query, rowMapper, id);

        // gambiarra
        if(lista.size() <= 0){
            return null;
        }
        return lista.get(0);
    }

    public List<Usuario> findAll(){
        String query = "SELECT u.id, u.nome, u.email, " +
                            "CASE " +
                                "WHEN p.id IS NOT NULL THEN 'professor' " +
                                "WHEN a.id IS NOT NULL THEN 'aluno' " +
                            "END AS tipo " +
                        "FROM usuario u LEFT JOIN professor p on u.id = p.id " +
                        "LEFT JOIN aluno a on u.id = a.id";
        List<Usuario> lista = new ArrayList<>();
        lista = jdbcTemplate.query(query, rowMapper);

        if(lista.size() <= 0){
            return null;
        }
        return lista;
    }
    public void update(Usuario usuario) {
        if (usuario.getSenha() != null && !usuario.getSenha().isEmpty()) {
            String query = "UPDATE usuario SET nome = ?, email = ?, senha = ? WHERE id = ?";
            jdbcTemplate.update(query, usuario.getNome(), usuario.getEmail(), usuario.getSenha(), usuario.getId());
        } 
        else {
            String query = "UPDATE usuario SET nome = ?, email = ? WHERE id = ?";
            jdbcTemplate.update(query, usuario.getNome(), usuario.getEmail(), usuario.getId());
        }
    }

    @Transactional
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM professor WHERE id = ?", id);
        jdbcTemplate.update("DELETE FROM aluno WHERE id = ?", id);
        jdbcTemplate.update("DELETE FROM usuario WHERE id = ?", id);
    }

    public boolean verificarCadastro(String email){
        // gambiarra
        Usuario usuario = findByEmail(email);
        if(usuario == null){
            return false;
        }
        return true;
    }


    public Map<String, Object> login(String email, String senha) {
    String query = "SELECT u.id, u.nome, u.email, " +
               "CASE " +
                   "WHEN p.id IS NOT NULL THEN 'professor' " +
                   "WHEN a.id IS NOT NULL THEN 'aluno' " +
                   "ELSE 'usuario' " +
               "END AS tipo " +
        "FROM usuario u " + 
        "LEFT JOIN professor p ON p.id = u.id " +
        "LEFT JOIN aluno a ON a.id = u.id " +
        "WHERE u.email = ? AND u.senha = ?";

        
    List<Map<String, Object>> resultados = jdbcTemplate.queryForList(query, email, senha);
    if (resultados.isEmpty()) {
        return null;
    }
    return resultados.get(0);
}

}
