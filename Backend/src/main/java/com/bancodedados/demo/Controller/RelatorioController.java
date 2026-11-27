package com.bancodedados.demo.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

import com.bancodedados.demo.DAO.RelatorioDAO;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    private final RelatorioDAO relatorioDAO;

    public RelatorioController(RelatorioDAO relatorioDAO) {
        this.relatorioDAO = relatorioDAO;
    }

    @GetMapping("/geral")
    public Map<String, Object> resumoGeral() {
        return relatorioDAO.resumoGeral();
    }

    @GetMapping("/avaliacoes")
    public List<Map<String, Object>> resumoPorAvaliacao() {
        return relatorioDAO.resumoPorAvaliacao();
    }

    @GetMapping("/avaliacoes/{id}/notas")
    public List<Map<String, Object>> notasPorAvaliacao(@PathVariable Long id) {
        return relatorioDAO.notasPorAvaliacao(id);
    }

    @GetMapping("/avaliacoes/{id}/questoes")
    public List<Map<String, Object>> desempenhoPorQuestao(@PathVariable Long id) {
        return relatorioDAO.desempenhoPorQuestao(id);
    }
}