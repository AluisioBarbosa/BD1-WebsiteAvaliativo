package com.bancodedados.demo.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.bancodedados.demo.DAO.AlternativaDAO;
import com.bancodedados.demo.DAO.QuestaoDAO;
import com.bancodedados.demo.Model.Alternativa;
import com.bancodedados.demo.Model.Questao;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/questoes")
public class QuestaoController {

    private final QuestaoDAO questaoDAO;
    private final AlternativaDAO alternativaDAO;

    public QuestaoController(QuestaoDAO questaoDAO, AlternativaDAO alternativaDAO) {
        this.questaoDAO = questaoDAO;
        this.alternativaDAO = alternativaDAO;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Long> criar(@RequestBody Questao questao) {
        Long idQuestao = questaoDAO.save(questao);

        if (questao.getAlternativas() != null) {
            for (Alternativa alt : questao.getAlternativas()) {
                alt.setIdQuestao(idQuestao);
                alternativaDAO.save(alt);
            }
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(idQuestao);
    }

    @GetMapping
    public List<Questao> listarTodas() {
        List<Questao> questoes = questaoDAO.findAll();

        for (Questao q : questoes) {
            q.setAlternativas(alternativaDAO.findByQuestao(q.getId()));
        }

        return questoes;
    }

    @GetMapping("/{id}")
    public Questao buscarPorId(@PathVariable Long id) {
        Questao questao = questaoDAO.findByID(id);
        if (questao != null) {
            questao.setAlternativas(alternativaDAO.findByQuestao(id));
        }
        return questao;
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