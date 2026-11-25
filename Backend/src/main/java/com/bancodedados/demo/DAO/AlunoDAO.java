package com.bancodedados.demo.DAO;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bancodedados.demo.Model.Aluno;

@Repository 
public class AlunoDAO {
    private final JdbcTemplate jdbcTemplate;

    public AlunoDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }


    private final RowMapper<Aluno> rowMapper = (rs, rowNum) -> {
        Aluno aluno = new Aluno();
        aluno.setId(rs.getLong("id"));
        aluno.setNome(rs.getString("nome"));
        aluno.setEmail(rs.getString("email"));
        return aluno;
    };


    // Primeiro põe na tabela pai Usuario e depois põe o id referenciando na aluno, Usuario -> Aluno
    @Transactional 
    public void save(Aluno aluno){
        String queryTabelaUsuario = "INSERT INTO usuario (nome, email, senha) VALUES (?, ?, ?) RETURNING id";
        Long id = jdbcTemplate.queryForObject(queryTabelaUsuario, Long.class, aluno.getNome(), aluno.getEmail(), aluno.getSenha());
        String queryTabelaAluno = "INSERT INTO aluno (id) VALUES (?)";
        jdbcTemplate.update(queryTabelaAluno, id);
    }

    // Nao pode usar o SELECT * pq se nao vem a senha junto
    public Aluno findByID(Long id){
        String query = "SELECT u.id, u.nome, u.email FROM usuario u INNER JOIN aluno a ON u.id = a.id WHERE u.id = ?";
        return jdbcTemplate.queryForObject(query, rowMapper, id);
    }

    public List<Aluno> findAll(){
        String query = "SELECT u.id, u.nome, u.email FROM usuario u " +
        "INNER JOIN aluno a ON u.id = a.id ORDER BY u.nome";

        List<Aluno> lista = new ArrayList<>();
        lista = jdbcTemplate.query(query, rowMapper);

        return lista;
    }

    @Transactional 
    public void delete(Long id){
        String queryUsuario = "DELETE from usuario where id = ?";
        String queryAluno = "DELETE from aluno WHERE id = ?";
        jdbcTemplate.update(queryAluno, id);
        jdbcTemplate.update(queryUsuario, id);
    }

    public void update(Aluno aluno){
        String query = "UPDATE usuario SET nome = ?, email = ?, senha = ? WHERE id = ?";
        jdbcTemplate.update(query, aluno.getNome(), aluno.getEmail(), aluno.getSenha(), aluno.getId());
    }

}
