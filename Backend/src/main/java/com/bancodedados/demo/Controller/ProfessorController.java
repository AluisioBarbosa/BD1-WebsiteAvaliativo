package com.bancodedados.demo.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bancodedados.demo.DAO.ProfessorDAO;
import com.bancodedados.demo.Model.Professor;

@RestController
@RequestMapping("/professores")
public class ProfessorController {

    private final ProfessorDAO professorDAO;

    public ProfessorController(ProfessorDAO professorDAO) {
        this.professorDAO = professorDAO;
    }

    @PostMapping
    public ResponseEntity<Void> criar(@RequestBody Professor professor) {
        professorDAO.save(professor);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public List<Professor> listarTodos() {
        return professorDAO.findAll();
    }

    @GetMapping("/{id}")
    public Professor buscarPorId(@PathVariable Long id) {
        return professorDAO.findByID(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Long id, @RequestBody Professor professor) {
        professor.setId(id);
        professorDAO.update(professor);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        professorDAO.delete(id);
        return ResponseEntity.noContent().build();
    }
}