package com.bancodedados.demo.DAO;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bancodedados.demo.Model.Avaliacao;

@Repository
public class AvaliacaoDAO {
    private final JdbcTemplate jdbcTemplate;

    public AvaliacaoDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Avaliacao> rowMapper = (rs, rowNum) -> {
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setId(rs.getLong("id"));
        avaliacao.setTitulo(rs.getString("titulo"));
        avaliacao.setDescricao(rs.getString("descricao"));
        avaliacao.setHoraInicio(rs.getTimestamp("hora_inicio").toLocalDateTime());
        avaliacao.setHoraFim(rs.getTimestamp("hora_fim").toLocalDateTime());
        avaliacao.setIdProfessor(rs.getLong("id_professor"));
        return avaliacao;
    };

    public Long save(Avaliacao avaliacao) {
        String query = "INSERT INTO avaliacao (titulo, descricao, hora_inicio, hora_fim, id_professor) VALUES (?, ?, ?, ?, ?) RETURNING id";
        return jdbcTemplate.queryForObject(query, Long.class, avaliacao.getTitulo(), avaliacao.getDescricao(), Timestamp.valueOf(avaliacao.getHoraInicio()),
                                        Timestamp.valueOf(avaliacao.getHoraFim()), avaliacao.getIdProfessor());
    }

    public Avaliacao findByID(Long id) {
        String query = "SELECT id, titulo, descricao, hora_inicio, hora_fim, id_professor FROM avaliacao WHERE id = ?";
        return jdbcTemplate.queryForObject(query, rowMapper, id);
    }

    public List<Avaliacao> findAll() {
        String query = "SELECT id, titulo, descricao, hora_inicio, hora_fim, id_professor FROM avaliacao ORDER BY hora_inicio DESC";
        return jdbcTemplate.query(query, rowMapper);
    }

    public List<Avaliacao> findByProfessor(Long idProfessor) {
        String query = "SELECT id, titulo, descricao, hora_inicio, hora_fim, id_professor FROM avaliacao WHERE id_professor = ? ORDER BY hora_inicio DESC";
        return jdbcTemplate.query(query, rowMapper, idProfessor);
    }

    public void update(Avaliacao avaliacao) {
        String query = "UPDATE avaliacao SET titulo = ?, descricao = ?, hora_inicio = ?, hora_fim = ? WHERE id = ?";
        jdbcTemplate.update(query, avaliacao.getTitulo(), avaliacao.getDescricao(), Timestamp.valueOf(avaliacao.getHoraInicio()),
                            Timestamp.valueOf(avaliacao.getHoraFim()), avaliacao.getId());
    }

    @Transactional
    public void delete(Long id) {
        String query = "DELETE FROM avaliacao WHERE id = ?";
        jdbcTemplate.update(query, id);
    }
}
