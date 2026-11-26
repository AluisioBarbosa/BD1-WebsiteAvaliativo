package com.bancodedados.demo.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bancodedados.demo.DAO.RealizaDAO;
import com.bancodedados.demo.Model.Realiza;

@RestController
@RequestMapping("/realiza")
public class RealizaController {

    private final RealizaDAO realizaDAO;

    public RealizaController(RealizaDAO realizaDAO) {
        this.realizaDAO = realizaDAO;
    }

    @PostMapping
    public ResponseEntity<Void> criar(@RequestBody Realiza realiza) {
        realizaDAO.save(realiza);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public List<Realiza> listarTodos() {
        return realizaDAO.findAll();
    }

    @GetMapping("/aluno/{idAluno}")
    public List<Realiza> listarPorAluno(@PathVariable Long idAluno) {
        return realizaDAO.findByAluno(idAluno);
    }

    @GetMapping("/avaliacao/{idAvaliacao}")
    public List<Realiza> listarPorAvaliacao(@PathVariable Long idAvaliacao) {
        return realizaDAO.findByAvaliacao(idAvaliacao);
    }

    @GetMapping("/{idAluno}/{idAvaliacao}")
    public Realiza buscarPorId(@PathVariable Long idAluno, @PathVariable Long idAvaliacao) {
        return realizaDAO.findByID(idAluno, idAvaliacao);
    }

    @PutMapping("/{idAluno}/{idAvaliacao}")
    public ResponseEntity<Void> atualizar(
            @PathVariable Long idAluno,
            @PathVariable Long idAvaliacao,
            @RequestBody Realiza realiza) {
        realiza.setIdAluno(idAluno);
        realiza.setIdAvaliacao(idAvaliacao);
        realizaDAO.update(realiza);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{idAluno}/{idAvaliacao}")
    public ResponseEntity<Void> deletar(@PathVariable Long idAluno, @PathVariable Long idAvaliacao) {
        realizaDAO.delete(idAluno, idAvaliacao);
        return ResponseEntity.noContent().build();
    }
}