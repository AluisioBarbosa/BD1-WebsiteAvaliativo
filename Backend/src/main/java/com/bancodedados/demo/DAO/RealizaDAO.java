package com.bancodedados.demo.DAO;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bancodedados.demo.Model.Realiza;

@Repository
public class RealizaDAO {

    private final JdbcTemplate jdbcTemplate;

    public RealizaDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Realiza> rowMapper = (rs, rowNum) -> {
        Realiza realiza = new Realiza();
        realiza.setIdAluno(rs.getLong("id_aluno"));
        realiza.setIdAvaliacao(rs.getLong("id_avaliacao"));
        realiza.setStatusAvaliacao(rs.getString("status_avaliacao"));
        return realiza;
    };

    public void save(Realiza realiza) {
        String query = "INSERT INTO realiza (id_aluno, id_avaliacao, status_avaliacao) " +
                       "VALUES (?, ?, ?)";
        jdbcTemplate.update( query, realiza.getIdAluno(), realiza.getIdAvaliacao(), realiza.getStatusAvaliacao());
    }

    public Realiza findByID(Long idAluno, Long idAvaliacao) {
        String query = "SELECT id_aluno, id_avaliacao, status_avaliacao " +
                       "FROM realiza WHERE id_aluno = ? AND id_avaliacao = ?";
        return jdbcTemplate.queryForObject(query, rowMapper, idAluno, idAvaliacao);
    }

    public List<Realiza> findAll() {
        String query = "SELECT id_aluno, id_avaliacao, status_avaliacao FROM realiza";
        return jdbcTemplate.query(query, rowMapper);
    }

    public List<Realiza> findByAluno(Long idAluno) {
        String query = "SELECT id_aluno, id_avaliacao, status_avaliacao FROM realiza WHERE id_aluno = ?";
        return jdbcTemplate.query(query, rowMapper, idAluno);
    }

    public List<Realiza> findByAvaliacao(Long idAvaliacao) {
        String query = "SELECT id_aluno, id_avaliacao, status_avaliacao FROM realiza WHERE id_avaliacao = ?";
        return jdbcTemplate.query(query, rowMapper, idAvaliacao);
    }

    public void update(Realiza realiza) {
        String query = "UPDATE realiza SET status_avaliacao = ? WHERE id_aluno = ? AND id_avaliacao = ?";
        jdbcTemplate.update(query, realiza.getStatusAvaliacao(), realiza.getIdAluno(), realiza.getIdAvaliacao());
    }

    public void delete(Long idAluno, Long idAvaliacao) {
        String query = "DELETE FROM realiza WHERE id_aluno = ? AND id_avaliacao = ?";
        jdbcTemplate.update(query, idAluno, idAvaliacao);
    }
}