package com.bancodedados.demo.Controller;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.bancodedados.demo.DAO.AlternativaDAO;
import com.bancodedados.demo.DAO.AvaliacaoDAO;
import com.bancodedados.demo.DAO.AvaliacaoQuestaoDAO;
import com.bancodedados.demo.DAO.RealizaDAO;
import com.bancodedados.demo.Model.Avaliacao;
import com.bancodedados.demo.Model.AvaliacaoQuestao;
import com.bancodedados.demo.Model.Realiza;

@RestController
@RequestMapping("/avaliacoes")
@CrossOrigin(origins = "*")
public class AvaliacaoController {

    private final AvaliacaoDAO avaliacaoDAO;
    private final AvaliacaoQuestaoDAO avaliacaoQuestaoDAO;
    private final RealizaDAO realizaDAO;
    private final AlternativaDAO alternativaDAO;

    public AvaliacaoController(AvaliacaoDAO avaliacaoDAO, AvaliacaoQuestaoDAO avaliacaoQuestaoDAO,
                                RealizaDAO realizaDAO, AlternativaDAO alternativaDAO) {
        this.avaliacaoDAO = avaliacaoDAO;
        this.avaliacaoQuestaoDAO = avaliacaoQuestaoDAO;
        this.realizaDAO = realizaDAO;
        this.alternativaDAO = alternativaDAO;
    }

    @GetMapping
    public List<Avaliacao>listarTodas() {
        return avaliacaoDAO.findAll();
    }

    @GetMapping("/{id}")
    public Avaliacao buscarPorId(@PathVariable Long id) {
        return avaliacaoDAO.findByID(id);
    }

    @GetMapping("/professor/{id}")
    public List<Avaliacao> listarPorProfessor(@PathVariable Long id) {
        return avaliacaoDAO.findByProfessor(id);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Long>criar(@RequestBody Map<String, Object>dados) {
        String data = (String) dados.get("data");
        String horaInicioStr = (String) dados.get("horaInicio");
        String horaFimStr = (String) dados.get("horaFim");

        LocalDateTime inicio;
        if (horaInicioStr.contains("T")) {
            inicio = LocalDateTime.parse(horaInicioStr);
        } 
        else {
            inicio = LocalDateTime.parse(data + "T" + horaInicioStr);
        }
        LocalDateTime fim;
        if (horaFimStr.contains("T")) {
            fim = LocalDateTime.parse(horaFimStr);
        } 
        else {
            fim = LocalDateTime.parse(data + "T" + horaFimStr);
        }

        Avaliacao av = new Avaliacao();
        av.setTitulo((String) dados.get("titulo"));
        av.setDescricao((String) dados.get("descricao"));
        av.setHoraInicio(inicio);
        av.setHoraFim(fim);
        av.setIdProfessor(((Number) dados.get("idProfessor")).longValue());

        Long idAvaliacao = avaliacaoDAO.save(av);

        List<Map<String, Object>> questoes = (List<Map<String, Object>>) dados.get("questoes");
        if (questoes != null) {
            int ordem = 1;
            for (Map<String, Object>q : questoes) {
                AvaliacaoQuestao aq = new AvaliacaoQuestao();
                aq.setIdAvaliacao(idAvaliacao);
                aq.setIdQuestao(((Number) q.get("idQuestao")).longValue());
                aq.setValor(((Number) q.get("valor")).doubleValue());
                if (q.get("ordemQuestoes") != null) {
                    aq.setOrdemQuestoes(((Number) q.get("ordemQuestoes")).intValue());
                } 
                else {
                    aq.setOrdemQuestoes(ordem);
                    ordem++;
                }
                avaliacaoQuestaoDAO.save(aq);
            }
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(idAvaliacao);
    }

    @PostMapping("/{id}/alunos")
    @Transactional
    public ResponseEntity<Void>vincularAlunos( @PathVariable Long id, @RequestBody List<Long>idsAlunos) {
        for (Long idAluno : idsAlunos) {
            Realiza realiza = new Realiza();
            realiza.setIdAluno(idAluno);
            realiza.setIdAvaliacao(id);
            realiza.setStatusAvaliacao("PENDENTE");
            realizaDAO.save(realiza);
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/aluno/{id}")
    public List<Map<String, Object>>minhasProvas(@PathVariable Long id) {
        return avaliacaoDAO.findByAluno(id);
    }

    @GetMapping("/{id}/responder")
    public Map<String, Object>montarProva(@PathVariable Long id) {
        Avaliacao avaliacao = avaliacaoDAO.findByID(id);
        List<Map<String, Object>>questoes = avaliacaoDAO.findQuestoesParaResponder(id);

        for (Map<String, Object>q : questoes) {
            Long idQuestao = ((Number) q.get("id")).longValue();
            q.put("alternativas", alternativaDAO.findByQuestao(idQuestao));
        }

        Map<String, Object>resposta = new LinkedHashMap<>();
        resposta.put("avaliacao", avaliacao);
        resposta.put("questoes", questoes);
        return resposta;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Long id, @RequestBody Avaliacao avaliacao) {
        avaliacao.setId(id);
        avaliacaoDAO.update(avaliacao);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>deletar(@PathVariable Long id) {
        avaliacaoDAO.delete(id);
        return ResponseEntity.noContent().build();
    }
}