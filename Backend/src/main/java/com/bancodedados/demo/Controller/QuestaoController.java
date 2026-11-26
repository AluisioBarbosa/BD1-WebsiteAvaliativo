package com.bancodedados.demo.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bancodedados.demo.DAO.QuestaoDAO;
import com.bancodedados.demo.Model.Questao;

@RestController
@RequestMapping("/questoes")
public class QuestaoController {

    private final QuestaoDAO questaoDAO;

    public QuestaoController(QuestaoDAO questaoDAO) {
        this.questaoDAO = questaoDAO;
    }

    @PostMapping
    public ResponseEntity<Long> criar(@RequestBody Questao questao) {
        Long id = questaoDAO.save(questao);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @GetMapping
    public List<Questao> listarTodas() {
        return questaoDAO.findAll();
    }

    @GetMapping("/{id}")
    public Questao buscarPorId(@PathVariable Long id) {
        return questaoDAO.findByID(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Long id, @RequestBody Questao questao) {
        questao.setId(id);
        questaoDAO.update(questao);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        questaoDAO.delete(id);
        return ResponseEntity.noContent().build();
    }
}