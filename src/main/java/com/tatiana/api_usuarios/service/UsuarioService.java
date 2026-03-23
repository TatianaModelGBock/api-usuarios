package com.tatiana.api_usuarios.service;

import com.tatiana.api_usuarios.dto.UsuarioDTO;
import com.tatiana.api_usuarios.model.Usuario;
import com.tatiana.api_usuarios.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    private final AtomicLong sequencia = new AtomicLong(1L);

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public void criarUsuario(UsuarioDTO dto) {

        Usuario usuario = new Usuario(sequencia.getAndIncrement(), dto.getNome(), dto.getEmail());


        repository.salvar(usuario);
    }
    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = repository.listar();
        usuarios.sort(Comparator.comparing(Usuario::getNome));
        return usuarios;
    }

    public void deletarUsuario(Long id) {

        Usuario usuario = repository.buscarPorId(id);

        if(usuario == null){
            throw new IllegalArgumentException("Usuário não encontrado");
        }

        repository.deletar(id);
    }

    public void atualizarUsuario(Long id, UsuarioDTO dto) {

        Usuario usuario = repository.buscarPorId(id);

        if(usuario == null){
            throw new IllegalArgumentException("Usuário não encontrado");
        }

        Usuario usuarioAtualizado = new Usuario(id, dto.getNome(), dto.getEmail());

        repository.atualizar(id, usuarioAtualizado);
    }
}
