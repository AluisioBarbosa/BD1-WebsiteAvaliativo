package com.bancodedados.demo.DAO;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bancodedados.demo.Model.Professor;

@Repository 
public class ProfessorDAO {
    private final JdbcTemplate jdbcTemplate;

    public ProfessorDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }


    private final RowMapper<Professor> rowMapper = (rs, rowNum) -> {
        Professor professor = new Professor();
        professor.setId(rs.getLong("id"));
        professor.setNome(rs.getString("nome"));
        professor.setEmail(rs.getString("email"));
        return professor;
    };


    // Primeiro põe na tabela pai Usuario e depois põe o id referenciando na professor, Usuario -> professor
    @Transactional 
    public void save(Professor professor){
        String queryTabelaUsuario = "INSERT INTO usuario (nome, email, senha) VALUES (?, ?, ?) RETURNING id";
        Long id = jdbcTemplate.queryForObject(queryTabelaUsuario, Long.class, professor.getNome(), professor.getEmail(), professor.getSenha());
        String queryTabelaProfessor = "INSERT INTO professor (id) VALUES (?)";
        jdbcTemplate.update(queryTabelaProfessor, id);
    }

    // Nao pode usar o SELECT * pq se nao vem a senha junto
    public Professor findByID(Long id){
        String query = "SELECT u.id, u.nome, u.email FROM usuario u INNER JOIN professor p ON u.id = p.id WHERE u.id = ?";
        return jdbcTemplate.queryForObject(query, rowMapper, id);
    }

    public List<Professor> findAll(){
        String query = "SELECT u.id, u.nome, u.email FROM usuario u " +
        "INNER JOIN professor p ON u.id = p.id ORDER BY u.nome";

        List<Professor> lista = new ArrayList<>();
        lista = jdbcTemplate.query(query, rowMapper);

        return lista;
    }

    @Transactional 
    public void delete(Long id){
        String queryUsuario = "DELETE from usuario where id = ?";
        String queryProfessor = "DELETE from professor WHERE id = ?";
        jdbcTemplate.update(queryProfessor, id);
        jdbcTemplate.update(queryUsuario, id);
    }

    public void update(Professor professor){
        String query = "UPDATE usuario SET nome = ?, email = ?, senha = ? WHERE id = ?";
        jdbcTemplate.update(query, professor.getNome(), professor.getEmail(), professor.getSenha(), professor.getId());
    }

}
