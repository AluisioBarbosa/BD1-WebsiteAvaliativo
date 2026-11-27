package com.bancodedados.demo.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.bancodedados.demo.DAO.RealizaDAO;
import com.bancodedados.demo.DAO.RespostaDAO;
import com.bancodedados.demo.Model.Realiza;
import com.bancodedados.demo.Model.Resposta;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/respostas")
public class RespostaController {

    private final RespostaDAO respostaDAO;
    private final RealizaDAO realizaDAO;

    public RespostaController(RespostaDAO respostaDAO, RealizaDAO realizaDAO) {
        this.respostaDAO = respostaDAO;
        this.realizaDAO = realizaDAO;
    }

    @PostMapping
    public ResponseEntity<Long> criar(@RequestBody Resposta resposta) {
        Long id = respostaDAO.save(resposta);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @PostMapping("/salvarRespostaProva")
    @Transactional
    @SuppressWarnings("unchecked")
    public ResponseEntity<Void> salvarRespostaProva(@RequestBody Map<String, Object> dados) {
        Long idAluno = ((Number) dados.get("idAluno")).longValue();
        Long idAvaliacao = ((Number) dados.get("idAvaliacao")).longValue();
        List<Map<String, Object>> respostas = (List<Map<String, Object>>) dados.get("respostas");

        // se por algum a caso a respota ja tiver sido respondida da erro né
        Realiza atual = realizaDAO.findByID(idAluno, idAvaliacao);
        if ("CONCLUIDA".equals(atual.getStatusAvaliacao())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        for (Map<String, Object> item : respostas) {
            Resposta resp = new Resposta();
            resp.setIdAluno(idAluno);
            resp.setIdAvaliacao(idAvaliacao);
            Long idQuestao = ((Number) item.get("idQuestao")).longValue();
            resp.setIdQuestao(idQuestao);

            if (item.get("idAlternativa") != null) {
                Long idAlt = ((Number) item.get("idAlternativa")).longValue();
                resp.setIdAlternativa(idAlt);
                resp.setNota(respostaDAO.calculaNotaQuestao(idAvaliacao, idQuestao, idAlt));
            } 
            else {
                resp.setTextoResposta((String) item.get("textoResposta"));
                resp.setNota(null);
            }

            respostaDAO.save(resp);
        }

        Realiza realiza = new Realiza();
        realiza.setIdAluno(idAluno);
        realiza.setIdAvaliacao(idAvaliacao);
        realiza.setStatusAvaliacao("CONCLUIDA");
        realizaDAO.update(realiza);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public List<Resposta> listarTodas() {
        return respostaDAO.findAll();
    }

    @GetMapping("/{id}")
    public Resposta buscarPorId(@PathVariable Long id) {
        return respostaDAO.findByID(id);
    }

    @GetMapping("/aluno/{idAluno}/avaliacao/{idAvaliacao}")
    public List<Resposta> listarPorAlunoEAvaliacao(
            @PathVariable Long idAluno,
            @PathVariable Long idAvaliacao) {
        return respostaDAO.findByAlunoEAvaliacao(idAluno, idAvaliacao);
    }

    @GetMapping("/textoLivre")
    public List<Map<String, Object>> listarTextoLivre() {
        return respostaDAO.findtextoLivre();
    }

    @GetMapping("/aluno/{id}/resultados")
    public List<Map<String, Object>> meuResultado(@PathVariable Long id) {
        return respostaDAO.findResultadosPorAluno(id);
    }

    @GetMapping("/aluno/{idAluno}/avaliacao/{idAvaliacao}/nota")
    public Double calcularNotaTotal(
            @PathVariable Long idAluno,
            @PathVariable Long idAvaliacao) {
        return respostaDAO.calcularNotaTotal(idAluno, idAvaliacao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Long id, @RequestBody Resposta resposta) {
        resposta.setId(id);
        respostaDAO.update(resposta);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/nota")
    public ResponseEntity<Void> atribuirNota(@PathVariable Long id, @RequestParam Double nota) {
        respostaDAO.atribuirNota(id, nota);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        respostaDAO.delete(id);
        return ResponseEntity.noContent().build();
    }
}