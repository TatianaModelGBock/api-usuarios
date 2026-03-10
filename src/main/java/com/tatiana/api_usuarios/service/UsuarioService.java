package com.tatiana.api_usuarios.service;

import com.tatiana.api_usuarios.dto.UsuarioDTO;
import com.tatiana.api_usuarios.model.Usuario;
import com.tatiana.api_usuarios.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository
        ;
    }

    public void criarUsuario(UsuarioDTO dto ) {

        Usuario usuario = new Usuario(System.currentTimeMillis(), dto.getNome(), dto.getEmail());
        repository.salvar(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return repository.listar();
    }
}
