package com.tatiana.api_usuarios.repository;

import com.tatiana.api_usuarios.model.Produto;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProdutoRepository {

    private final List<Produto> produtos = new ArrayList<>();

    public void salvar(Produto produto) {
        produtos.add(produto);
    }

    public List<Produto> listar() {
        return produtos;
    }

    public void deletar(Long id) {
        produtos.removeIf(produto -> produto.getId().equals(id));
    }

    public void atualizar(Long id, Produto produtoAtualizado) {
        for (int i = 0; i < produtos.size(); i++) {
            if (produtos.get(i).getId().equals(id)) {
                produtos.set(i, produtoAtualizado);
                break;
            }
        }
    }

    public Produto buscarPorId(Long id) {
        for (Produto produto : produtos) {
            if (produto.getId() != null && produto.getId().equals(id)) {
                return produto;
            }
        }
        return null;
    }
}

