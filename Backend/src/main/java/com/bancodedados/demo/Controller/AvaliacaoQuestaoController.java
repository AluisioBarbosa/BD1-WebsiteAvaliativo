package com.bancodedados.demo.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bancodedados.demo.DAO.AvaliacaoQuestaoDAO;
import com.bancodedados.demo.Model.AvaliacaoQuestao;

@RestController
@RequestMapping("/avaliacao-questoes")
public class AvaliacaoQuestaoController {

    private final AvaliacaoQuestaoDAO avaliacaoQuestaoDAO;

    public AvaliacaoQuestaoController(AvaliacaoQuestaoDAO avaliacaoQuestaoDAO) {
        this.avaliacaoQuestaoDAO = avaliacaoQuestaoDAO;
    }

    @PostMapping
    public ResponseEntity<Void> criar(@RequestBody AvaliacaoQuestao avaliacaoQuestao) {
        avaliacaoQuestaoDAO.save(avaliacaoQuestao);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public List<AvaliacaoQuestao> listarTodos() {
        return avaliacaoQuestaoDAO.findAll();
    }

    @GetMapping("/avaliacao/{idAvaliacao}")
    public List<AvaliacaoQuestao> listarPorAvaliacao(@PathVariable Long idAvaliacao) {
        return avaliacaoQuestaoDAO.findByAvaliacao(idAvaliacao);
    }

    @GetMapping("/{idAvaliacao}/{idQuestao}")
    public AvaliacaoQuestao buscarPorId(@PathVariable Long idAvaliacao, @PathVariable Long idQuestao) {
        return avaliacaoQuestaoDAO.findByID(idAvaliacao, idQuestao);
    }

    @PutMapping("/{idAvaliacao}/{idQuestao}")
    public ResponseEntity<Void> atualizar(
            @PathVariable Long idAvaliacao,
            @PathVariable Long idQuestao,
            @RequestBody AvaliacaoQuestao avaliacaoQuestao) {
        avaliacaoQuestao.setIdAvaliacao(idAvaliacao);
        avaliacaoQuestao.setIdQuestao(idQuestao);
        avaliacaoQuestaoDAO.update(avaliacaoQuestao);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{idAvaliacao}/{idQuestao}")
    public ResponseEntity<Void> deletar(@PathVariable Long idAvaliacao, @PathVariable Long idQuestao) {
        avaliacaoQuestaoDAO.delete(idAvaliacao, idQuestao);
        return ResponseEntity.noContent().build();
    }
}