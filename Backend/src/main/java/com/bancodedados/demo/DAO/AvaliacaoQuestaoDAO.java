package com.bancodedados.demo.DAO;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bancodedados.demo.Model.AvaliacaoQuestao;

@Repository
public class AvaliacaoQuestaoDAO {

    private final JdbcTemplate jdbcTemplate;

    public AvaliacaoQuestaoDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<AvaliacaoQuestao> rowMapper = (rs, rowNum) -> {
        AvaliacaoQuestao avaliacaoQuestao = new AvaliacaoQuestao();
        avaliacaoQuestao.setIdAvaliacao(rs.getLong("id_avaliacao"));
        avaliacaoQuestao.setIdQuestao(rs.getLong("id_questao"));
        avaliacaoQuestao.setValor(rs.getObject("valor", Double.class));
        avaliacaoQuestao.setOrdemQuestoes(rs.getObject("ordem_questoes", Integer.class));
        return avaliacaoQuestao;
    };

    public void save(AvaliacaoQuestao avaliacaoQuestao) {
        String query = "INSERT INTO avaliacao_questao (id_avaliacao, id_questao, valor, ordem_questoes) " +
                       "VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(query, avaliacaoQuestao.getIdAvaliacao(), avaliacaoQuestao.getIdQuestao(), 
                            avaliacaoQuestao.getValor(), avaliacaoQuestao.getOrdemQuestoes());
    }

    public AvaliacaoQuestao findByID(Long idAvaliacao, Long idQuestao) {
        String query = "SELECT id_avaliacao, id_questao, valor, ordem_questoes FROM avaliacao_questao " +
                        "WHERE id_avaliacao = ? AND id_questao = ?";
        return jdbcTemplate.queryForObject(query, rowMapper, idAvaliacao, idQuestao);
    }

    public List<AvaliacaoQuestao> findAll() {
        String query = "SELECT id_avaliacao, id_questao, valor, ordem_questoes FROM avaliacao_questao";
        return jdbcTemplate.query(query, rowMapper);
    }

    public List<AvaliacaoQuestao> findByAvaliacao(Long idAvaliacao) {
        String query = "SELECT id_avaliacao, id_questao, valor, ordem_questoes FROM avaliacao_questao" +
                            "WHERE id_avaliacao = ? ORDER BY ordem_questoes";
        return jdbcTemplate.query(query, rowMapper, idAvaliacao);
    }

    public void update(AvaliacaoQuestao avaliacaoQuestao) {
        String query = "UPDATE avaliacao_questao SET valor = ?, ordem_questoes = ? " +
                       "WHERE id_avaliacao = ? AND id_questao = ?";
        jdbcTemplate.update(query, avaliacaoQuestao.getValor(), avaliacaoQuestao.getOrdemQuestoes(),
                            avaliacaoQuestao.getIdAvaliacao(), avaliacaoQuestao.getIdQuestao());
    }

    public void delete(Long idAvaliacao, Long idQuestao) {
        String query = "DELETE FROM avaliacao_questao WHERE id_avaliacao = ? AND id_questao = ?";
        jdbcTemplate.update(query, idAvaliacao, idQuestao);
    }
}