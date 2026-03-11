package com.tatiana.api_usuarios.repository;

import com.tatiana.api_usuarios.model.Usuario;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UsuarioRepository {

    private List<Usuario> usuarios = new ArrayList<>();

    public void salvar(Usuario usuario) {
        usuarios.add(usuario);
    }

    public List<Usuario> listar() {
        return usuarios;
    }

    public void deletar(Long id) {
        usuarios.removeIf(usuario -> usuario.getId().equals(id));
    }

    public void atualizar(Long id, Usuario usuarioAtualizado) {
        for(int i = 0; i < usuarios.size(); i++) {
            if(usuarios.get(i).getId().equals(id)) {
                usuarios.set(i, usuarioAtualizado);
            }
        }
    }

}
