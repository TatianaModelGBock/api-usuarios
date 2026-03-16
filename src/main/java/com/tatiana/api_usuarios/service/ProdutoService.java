package com.tatiana.api_usuarios.service;

import com.tatiana.api_usuarios.dto.ProdutoDTO;
import com.tatiana.api_usuarios.model.Produto;
import com.tatiana.api_usuarios.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }
    public void criarProduto(ProdutoDTO dto) {

        Produto produto = new Produto(System.currentTimeMillis(), dto.getNome(), dto.getPreco());

        repository.salvar(produto);
    }
    public List<Produto> listarProdutos() {
        return repository.listar();
    }
    public void deletarProduto(Long id) {
        repository.deletar(id);
    }
    public void atualizarProduto(Long id, ProdutoDTO dto) {

        Produto produtoAtualizado = new Produto(id, dto.getNome(), dto.getPreco());
        repository.atualizar(id, produtoAtualizado);
    }
}
