package com.bancodedados.demo.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bancodedados.demo.DAO.AlternativaDAO;
import com.bancodedados.demo.Model.Alternativa;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/alternativas")
public class AlternativaController {

    private final AlternativaDAO alternativaDAO;

    public AlternativaController(AlternativaDAO alternativaDAO) {
        this.alternativaDAO = alternativaDAO;
    }

    @PostMapping
    public ResponseEntity<Long> criar(@RequestBody Alternativa alternativa) {
        Long id = alternativaDAO.save(alternativa);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @GetMapping
    public List<Alternativa> listarTodas() {
        return alternativaDAO.findAll();
    }

    @GetMapping("/{id}")
    public Alternativa buscarPorId(@PathVariable Long id) {
        return alternativaDAO.findByID(id);
    }

    @GetMapping("/questao/{idQuestao}")
    public List<Alternativa> listarPorQuestao(@PathVariable Long idQuestao) {
        return alternativaDAO.findByQuestao(idQuestao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Long id, @RequestBody Alternativa alternativa) {
        alternativa.setId(id);
        alternativaDAO.update(alternativa);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        alternativaDAO.delete(id);
        return ResponseEntity.noContent().build();
    }
}