package com.bancodedados.demo.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bancodedados.demo.DAO.AvaliacaoDAO;
import com.bancodedados.demo.Model.Avaliacao;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoController {

    private final AvaliacaoDAO avaliacaoDAO;

    public AvaliacaoController(AvaliacaoDAO avaliacaoDAO) {
        this.avaliacaoDAO = avaliacaoDAO;
    }

    @PostMapping
    public ResponseEntity<Long> criar(@RequestBody Avaliacao avaliacao) {
        Long id = avaliacaoDAO.save(avaliacao);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @GetMapping
    public List<Avaliacao> listarTodas() {
        return avaliacaoDAO.findAll();
    }

    @GetMapping("/{id}")
    public Avaliacao buscarPorId(@PathVariable Long id) {
        return avaliacaoDAO.findByID(id);
    }

    @GetMapping("/professor/{idProfessor}")
    public List<Avaliacao> listarPorProfessor(@PathVariable Long idProfessor) {
        return avaliacaoDAO.findByProfessor(idProfessor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Long id, @RequestBody Avaliacao avaliacao) {
        avaliacao.setId(id);
        avaliacaoDAO.update(avaliacao);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        avaliacaoDAO.delete(id);
        return ResponseEntity.noContent().build();
    }
}