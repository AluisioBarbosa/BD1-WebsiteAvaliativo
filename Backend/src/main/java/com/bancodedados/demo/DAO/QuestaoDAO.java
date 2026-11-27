package com.bancodedados.demo.DAO;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bancodedados.demo.Model.Questao;

@Repository 
public class QuestaoDAO {
    private final JdbcTemplate jdbcTemplate;

    public QuestaoDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Questao> rowMapper = (rs, rowNum) -> {
        Questao questao = new Questao();
        questao.setId(rs.getLong("id"));
        questao.setEnunciado(rs.getString("enunciado"));
        questao.setTipo(rs.getString("tipo"));
        return questao;
    };

    public Long save(Questao questao) {
        String query = "INSERT INTO questao (enunciado, tipo) VALUES (?, ?) RETURNING id";
        return jdbcTemplate.queryForObject(query, Long.class, questao.getEnunciado(), questao.getTipo());
    }

    public Questao findByID(Long id) {
        String query = "SELECT id, enunciado, tipo FROM questao WHERE id = ?";
        return jdbcTemplate.queryForObject(query, rowMapper, id);
    }

    public List<Questao> findAll() {
        String query = "SELECT id, enunciado, tipo FROM questao ORDER BY id";
        return jdbcTemplate.query(query, rowMapper);
    }

    public void update(Questao questao) {
        String query = "UPDATE questao SET enunciado = ?, tipo = ? WHERE id = ?";
        jdbcTemplate.update(query, questao.getEnunciado(), questao.getTipo(), questao.getId());
    }

    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM alternativa WHERE idQuestao = ?", id);
        jdbcTemplate.update("DELETE FROM questao WHERE id = ?", id);
    }

    
}
