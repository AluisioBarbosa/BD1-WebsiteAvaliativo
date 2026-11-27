package com.bancodedados.demo.DAO;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RelatorioDAO {

    private final JdbcTemplate jdbcTemplate;

    public RelatorioDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String, Object>> resumoPorAvaliacao() {
        String query = "SELECT av.id AS id_avaliacao, av.titulo, " +
                   "COUNT(DISTINCT r.id_aluno) AS total_alunos, " +
                   "COUNT(DISTINCT CASE WHEN r.status_avaliacao = 'RESPONDIDA' THEN r.id_aluno END) AS total_respondidas, " +
                   "COALESCE(ROUND(AVG(sub.nota_aluno)::numeric, 2), 0.0) AS media_notas, " +
                   "COALESCE(MAX(sub.nota_aluno), 0.0) AS maior_nota, " +
                   "COALESCE(MIN(sub.nota_aluno), 0.0) AS menor_nota " +
            "FROM avaliacao av " +
            "LEFT JOIN realiza r ON r.id_avaliacao = av.id " +
            "LEFT JOIN ( " +
                "SELECT id_avaliacao, id_aluno, SUM(nota) AS nota_aluno " +
                "FROM resposta " +
                "GROUP BY id_avaliacao, id_aluno " +
            ") sub ON sub.id_avaliacao = av.id AND sub.id_aluno = r.id_aluno " +
            "GROUP BY av.id, av.titulo " +
            "ORDER BY av.id DESC";
        return jdbcTemplate.queryForList(query);
    }

    public List<Map<String, Object>> notasPorAvaliacao(Long idAvaliacao) {
        String query = "SELECT u.id AS id_aluno, u.nome AS nome_aluno, r.status_avaliacao, " +
                   "COALESCE(SUM(resp.nota), 0.0) AS nota_obtida, " +
                   "COALESCE((SELECT SUM(aq.valor) FROM avaliacao_questao aq WHERE aq.id_avaliacao = r.id_avaliacao), 0.0) " +
                    "AS valor_total, " +
                   "COUNT(CASE WHEN q.tipo = 'textoLivre' AND resp.nota IS NULL THEN 1 END) AS pendentes_correcao " +
            "FROM realiza r " +
            "JOIN usuario u ON u.id = r.id_aluno " +
            "LEFT JOIN resposta resp ON resp.id_aluno = r.id_aluno AND resp.id_avaliacao = r.id_avaliacao " +
            "LEFT JOIN questao q ON q.id = resp.id_questao " +
            "WHERE r.id_avaliacao = ? " +
            "GROUP BY u.id, u.nome, r.status_avaliacao, r.id_avaliacao " +
            "ORDER BY nota_obtida DESC, u.nome ";
        return jdbcTemplate.queryForList(query, idAvaliacao);
    }

    public List<Map<String, Object>> desempenhoPorQuestao(Long idAvaliacao) {
        String query = "SELECT q.id AS id_questao, q.enunciado, q.tipo, aq.valor AS valor_maximo, " +
                   "COUNT(resp.id) AS total_respostas, " +
                   "COALESCE(ROUND(AVG(resp.nota)::numeric, 2), 0.0) AS media_nota, " +
                   "COUNT(CASE WHEN resp.nota = aq.valor THEN 1 END) AS total_acertos " +
            "FROM avaliacao_questao aq " +
            "JOIN questao q ON q.id = aq.id_questao " +
            "LEFT JOIN resposta resp ON resp.id_questao = q.id AND resp.id_avaliacao = aq.id_avaliacao " +
            "WHERE aq.id_avaliacao = ? " +
            "GROUP BY q.id, q.enunciado, q.tipo, aq.valor, aq.ordem_questoes " +
            "ORDER BY aq.ordem_questoes";
        return jdbcTemplate.queryForList(query, idAvaliacao);
    }

    public Map<String, Object> resumoGeral() {
        String query ="SELECT (SELECT COUNT(*) FROM avaliacao) AS total_avaliacoes, " +
                   "(SELECT COUNT(*) FROM questao) AS total_questoes, " +
                   "(SELECT COUNT(*) FROM realiza WHERE status_avaliacao = 'RESPONDIDA') AS provas_entregues, " +
                   "(SELECT COUNT(*) FROM resposta r " +
                    "JOIN questao q ON q.id = r.id_questao " +
                    "WHERE q.tipo = 'textoLivre' AND r.nota IS NULL) AS texto_livre_pendentes";
        return jdbcTemplate.queryForMap(query);
    }
}