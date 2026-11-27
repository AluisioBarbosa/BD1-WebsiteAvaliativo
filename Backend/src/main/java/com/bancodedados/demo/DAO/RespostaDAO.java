package com.bancodedados.demo.DAO;

import java.util.List;
import java.util.Map;

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
        // se idAlternativa é null é pq a resposta é de texto corrido
        Object altObj = rs.getObject("id_alternativa");
        if (altObj != null) {
            resposta.setIdAlternativa(((Number) altObj).longValue());
        }
        resposta.setTextoResposta(rs.getString("texto_resposta"));
        Object notaObj = rs.getObject("nota");
        //Double quando é setado pra null ele vira 0, entao tem q meter isso aqui
        if (notaObj != null) {
            resposta.setNota(((Number) notaObj).doubleValue());
        }
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

    public void atribuirNota(Long idResposta, Double nota) {
        String query = "UPDATE resposta SET nota = ? WHERE id = ?";
        jdbcTemplate.update(query, nota, idResposta);
    }

    public double calcularNotaTotal(Long idAluno, Long idAvaliacao) {
        String query = "SELECT COALESCE(SUM(nota), 0) FROM resposta WHERE id_aluno = ? AND id_avaliacao = ?";
        return jdbcTemplate.queryForObject(query, Double.class, idAluno, idAvaliacao);
    }
    public Double calculaNotaQuestao(Long idAvaliacao, Long idQuestao, Long idAlternativa) {
        String query = "SELECT CASE WHEN alt.correta = TRUE THEN aq.valor ELSE 0.0 END FROM alternativa alt " +
            "JOIN avaliacao_questao aq " +
              "ON aq.id_questao = alt.id_questao AND aq.id_avaliacao = ? WHERE alt.id = ? AND alt.id_questao = ?";
        return jdbcTemplate.queryForObject(query, Double.class, idAvaliacao, idAlternativa, idQuestao);
    }

    public void delete(Long id) {
        String query = "DELETE FROM resposta WHERE id = ?";
        jdbcTemplate.update(query, id);
    }

    public void deleteByAlunoEAvaliacao(Long idAluno, Long idAvaliacao) {
        String query = "DELETE FROM resposta WHERE id_aluno = ? AND id_avaliacao = ?";
        jdbcTemplate.update(query, idAluno, idAvaliacao);
    }

    public List<Map<String, Object>> findtextoLivre() {
        String query = "SELECT r.id, r.id_aluno, u.nome AS nome_aluno, r.id_avaliacao, av.titulo AS titulo_avaliacao, " +
                   "r.id_questao, q.enunciado, r.texto_resposta, r.nota, aq.valor AS valor_maximo " +
            "FROM resposta r JOIN usuario u ON u.id = r.id_aluno JOIN avaliacao av ON av.id = r.id_avaliacao " +
            "JOIN questao q ON q.id = r.id_questao JOIN avaliacao_questao aq ON aq.id_avaliacao = r.id_avaliacao " +
            "AND aq.id_questao = r.id_questao WHERE q.tipo = 'DISSERTATIVA' ORDER BY r.nota NULLS FIRST, av.id, u.nome";
        return jdbcTemplate.queryForList(query);
    }

    public List<Map<String, Object>> findResultadosPorAluno(Long idAluno) {
        String query = "SELECT av.id AS id_avaliacao, av.titulo, r.status_avaliacao, COALESCE(SUM(resp.nota), 0.0) AS nota_obtida, " +
                   "COALESCE((SELECT SUM(aq.valor) FROM avaliacao_questao aq WHERE aq.id_avaliacao = av.id), 0.0) AS valor_total, " +
                   "COUNT(CASE WHEN q.tipo = 'DISSERTATIVA' AND resp.nota IS NULL THEN 1 END) AS pendentes_correcao " +
            "FROM realiza r " +
            "JOIN avaliacao av ON av.id = r.id_avaliacao " +
            "LEFT JOIN resposta resp ON resp.id_aluno = r.id_aluno AND resp.id_avaliacao = r.id_avaliacao " +
            "LEFT JOIN questao q ON q.id = resp.id_questao " +
            "WHERE r.id_aluno = ? " +
            "GROUP BY av.id, av.titulo, r.status_avaliacao " +
            "ORDER BY av.id ";
        return jdbcTemplate.queryForList(query, idAluno);
    }
}
