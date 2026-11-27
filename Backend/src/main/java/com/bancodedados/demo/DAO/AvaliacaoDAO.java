package com.bancodedados.demo.DAO;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

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

    public List<Map<String, Object>> findByAluno(Long idAluno) {
        String query = "SELECT a.id, a.titulo, a.descricao, a.hora_inicio, a.hora_fim, r.status_avaliacao FROM avaliacao a " +
                        "JOIN realiza r ON r.id_avaliacao = a.id WHERE r.id_aluno = ? ORDER BY a.hora_inicio DESC";
        return jdbcTemplate.queryForList(query, idAluno);
    }

    public List<Map<String, Object>> findQuestoesParaResponder(Long idAvaliacao) {
        String query = "SELECT q.id, q.enunciado, q.tipo, aq.valor, aq.ordem_questoes FROM avaliacao_questao aq JOIN questao q " +
                        "ON q.id = aq.id_questao WHERE aq.id_avaliacao = ? ORDER BY aq.ordem_questoes";
        return jdbcTemplate.queryForList(query, idAvaliacao);
    }

    public void update(Avaliacao avaliacao) {
        String query = "UPDATE avaliacao SET titulo = ?, descricao = ?, hora_inicio = ?, hora_fim = ? WHERE id = ?";
        jdbcTemplate.update(query, avaliacao.getTitulo(), avaliacao.getDescricao(), Timestamp.valueOf(avaliacao.getHoraInicio()),
                            Timestamp.valueOf(avaliacao.getHoraFim()), avaliacao.getId());
    }

    @Transactional
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM resposta WHERE id_avaliacao = ?", id);
        jdbcTemplate.update("DELETE FROM realiza WHERE id_avaliacao = ?", id);
        jdbcTemplate.update("DELETE FROM avaliacao_questao WHERE id_avaliacao = ?", id);
        jdbcTemplate.update("DELETE FROM avaliacao WHERE id = ?", id);
    }
}
