package com.bancodedados.demo.DAO;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bancodedados.demo.Model.Resposta;

@Repository 
public class RespostaDAO {
    private final JdbcTemplate jdbcTemplate;

    public RespostaDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Resposta> rowMapper = (rs, rowNum) -> {
        Resposta resposta = new Resposta();
        resposta.setId(rs.getLong("id"));
        resposta.setIdAluno(rs.getLong("id_aluno"));
        resposta.setIdAvaliacao(rs.getLong("id_avaliacao"));
        resposta.setIdQuestao(rs.getLong("id_questao"));
        resposta.setIdAlternativa(rs.getLong("id_alternativa"));
        resposta.setTextoResposta(rs.getString("texto_resposta"));
        resposta.setNota(rs.getDouble("nota"));
        return resposta;
    };

    public Long save(Resposta resposta) {
        String query = "INSERT INTO resposta (id_aluno, id_avaliacao, id_questao, id_alternativa, texto_resposta, nota) " +
                       "VALUES (?, ?, ?, ?, ?, ?) RETURNING id";
        return jdbcTemplate.queryForObject(query, Long.class, resposta.getIdAluno(), resposta.getIdAvaliacao(), resposta.getIdQuestao(), 
                                            resposta.getIdAlternativa(), resposta.getTextoResposta(), resposta.getNota());
    }

    public Resposta findByID(Long id) {
        String query = "SELECT id, id_aluno, id_avaliacao, id_questao, id_alternativa, texto_resposta, nota " +
                       "FROM resposta WHERE id = ?";
        return jdbcTemplate.queryForObject(query, rowMapper, id);
    }

    public List<Resposta> findAll() {
        String query = "SELECT id, id_aluno, id_avaliacao, id_questao, id_alternativa, texto_resposta, nota FROM resposta ORDER BY id";
        return jdbcTemplate.query(query, rowMapper);
    }

    public List<Resposta> findByAlunoEAvaliacao(Long idAluno, Long idAvaliacao) {
        String query = "SELECT id, id_aluno, id_avaliacao, id_questao, id_alternativa, texto_resposta, nota " +
                       "FROM resposta WHERE id_aluno = ? AND id_avaliacao = ? ORDER BY id";
        return jdbcTemplate.query(query, rowMapper, idAluno, idAvaliacao);
    }

    public void update(Resposta resposta) {
        String query = "UPDATE resposta SET id_alternativa = ?, texto_resposta = ?, nota = ? WHERE id = ?";
        jdbcTemplate.update(query, resposta.getIdAlternativa(), resposta.getTextoResposta(), resposta.getNota(), resposta.getId());
    }

    public void atribuirNota(Long idResposta, double nota) {
        String query = "UPDATE resposta SET nota = ? WHERE id = ?";
        jdbcTemplate.update(query, nota, idResposta);
    }

    public double calcularNotaTotal(Long idAluno, Long idAvaliacao) {
        String query = "SELECT COALESCE(SUM(nota), 0) FROM resposta WHERE id_aluno = ? AND id_avaliacao = ?";
        return jdbcTemplate.queryForObject(query, Double.class, idAluno, idAvaliacao);
    }

    public void delete(Long id) {
        String query = "DELETE FROM resposta WHERE id = ?";
        jdbcTemplate.update(query, id);
    }
}
