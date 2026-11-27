package com.bancodedados.demo.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bancodedados.demo.DAO.UsuarioDAO;
import com.bancodedados.demo.Model.Aluno;
import com.bancodedados.demo.Model.Professor;
import com.bancodedados.demo.Model.Usuario;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioDAO usuarioDAO;

    public UsuarioController(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    @PostMapping
    public ResponseEntity<Long> criar(@RequestBody Map<String, Object> dados) {
        String email = (String) dados.get("email");
        if (usuarioDAO.verificarCadastro(email)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        String tipo = (String) dados.get("tipo");
        Usuario usuario;
        if (tipo != null && tipo.equalsIgnoreCase("professor")) {
            usuario = new Professor();
        } 
        else {
            usuario = new Aluno();
        }

        usuario.setNome((String) dados.get("nome"));
        usuario.setEmail(email);
        usuario.setSenha((String) dados.get("senha"));

        Long id = usuarioDAO.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> usuarioLogin) {
        Map<String, Object> usuario = usuarioDAO.login(usuarioLogin.get("email"), usuarioLogin.get("senha"));
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(usuario);
    }

    @GetMapping
    public List<Usuario> listarTodos() {
        return usuarioDAO.findAll();
    }

    @GetMapping("/{id}")
    public Usuario buscarPorId(@PathVariable Long id) {
        return usuarioDAO.findById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
        usuario.setId(id);
        usuarioDAO.update(usuario);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        usuarioDAO.delete(id);
        return ResponseEntity.noContent().build();
    }


    
}