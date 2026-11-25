package com.bancodedados.demo.DAO;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.bancodedados.demo.Model.Alternativa;

public class AlternativaDAO {
    private final JdbcTemplate jdbcTemplate;

    public AlternativaDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Alternativa> rowMapper = (rs, rowNum) -> {
        Alternativa alternativa = new Alternativa();
        alternativa.setId(rs.getLong("id"));
        alternativa.setDescricao(rs.getString("descricao"));
        alternativa.setCorreta(rs.getBoolean("correta"));
        alternativa.setIdQuestao(rs.getLong("idQuestao"));
        return alternativa;
    };

    public Long save(Alternativa alternativa) {
        String query = "INSERT INTO alternativa (descricao, correta, idQuestao) VALUES (?, ?, ?) RETURNING id";
        return jdbcTemplate.queryForObject(query, Long.class, alternativa.getDescricao(), 
                                            alternativa.isCorreta(), alternativa.getIdQuestao());
    }

    public Alternativa findByID(Long id) {
        String query = "SELECT id, descricao, correta, idQuestao FROM alternativa WHERE id = ?";
        return jdbcTemplate.queryForObject(query, rowMapper, id);
    }

    public List<Alternativa> findAll() {
        String query = "SELECT id, descricao, correta, idQuestao FROM alternativa";
        return jdbcTemplate.query(query, rowMapper);
    }

    public List<Alternativa> findByQuestao(Long idQuestao) {
        String query = "SELECT id, descricao, correta, idQuestao FROM alternativa WHERE idQuestao = ?";
        return jdbcTemplate.query(query, rowMapper, idQuestao);
    }

    public void update(Alternativa alternativa) {
        String query = "UPDATE alternativa SET descricao = ?, correta = ? WHERE id = ?";
        jdbcTemplate.update(query, alternativa.getDescricao(), alternativa.isCorreta(), alternativa.getId());
    }

    public void delete(Long id) {
        String query = "DELETE FROM alternativa WHERE id = ?";
        jdbcTemplate.update(query, id);
    }
}
