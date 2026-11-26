package com.bancodedados.demo.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bancodedados.demo.DAO.RespostaDAO;
import com.bancodedados.demo.Model.Resposta;

@RestController
@RequestMapping("/respostas")
public class RespostaController {

    private final RespostaDAO respostaDAO;

    public RespostaController(RespostaDAO respostaDAO) {
        this.respostaDAO = respostaDAO;
    }

    @PostMapping
    public ResponseEntity<Long> criar(@RequestBody Resposta resposta) {
        Long id = respostaDAO.save(resposta);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
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