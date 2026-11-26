package com.bancodedados.demo.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bancodedados.demo.DAO.AlunoDAO;
import com.bancodedados.demo.Model.Aluno;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoDAO alunoDAO;

    public AlunoController(AlunoDAO alunoDAO) {
        this.alunoDAO = alunoDAO;
    }

    @PostMapping
    public ResponseEntity<Long> criar(@RequestBody Aluno aluno) {
        Long id = alunoDAO.save(aluno);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @GetMapping
    public List<Aluno> listarTodos() {
        return alunoDAO.findAll();
    }

    @GetMapping("/{id}")
    public Aluno buscarPorId(@PathVariable Long id) {
        return alunoDAO.findByID(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Long id, @RequestBody Aluno aluno) {
        aluno.setId(id);
        alunoDAO.update(aluno);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        alunoDAO.delete(id);
        return ResponseEntity.noContent().build();
    }
}