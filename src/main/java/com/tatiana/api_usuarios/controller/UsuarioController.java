package com.tatiana.api_usuarios.controller;

import com.tatiana.api_usuarios.dto.UsuarioDTO;
import com.tatiana.api_usuarios.model.Usuario;
import com.tatiana.api_usuarios.service.UsuarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping
    public String criarUsuario(@RequestBody UsuarioDTO dto) {
        service.criarUsuario(dto);

        return "Usuario criado com sucesso";
    }

    @GetMapping
    public List<Usuario> listarUsuarios() {
        return service.listarUsuarios();
    }

    @DeleteMapping("/{id}")
    public String deletarUsuario(@PathVariable Long id) {
        service.deletarUsuario(id);
        return "Usuario deletado com sucesso";
    }

    @PutMapping("/{id}")
    public String atualizarUsuario(@PathVariable Long id, @RequestBody UsuarioDTO dto) {
        service.atualizarUsuario(id, dto);
        return "Usuario atualizado com sucesso";
    }
}
