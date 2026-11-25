package com.bancodedados.demo.DAO;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

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
        if(tipo.equals("Professor")){
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
        if(tipo.equals("Professor")){
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

    private Usuario findByEmail(String email){
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

    private Usuario findById(Long id){
        String query = "SELECT u.id, u.nome, u.email " +
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

    private List<Usuario> findAll(){
        String query = "SELECT u.id, u.nome, u.email " +
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

    private boolean verificarCadastro(String email){
        // gambiarra
        Usuario usuario = findByEmail(email);
        if(usuario == null){
            return false;
        }
        return true;
    }

}
