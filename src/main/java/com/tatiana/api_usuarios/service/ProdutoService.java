package com.tatiana.api_usuarios.service;

import com.tatiana.api_usuarios.dto.ProdutoDTO;
import com.tatiana.api_usuarios.model.Produto;
import com.tatiana.api_usuarios.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;
    private final AtomicLong sequencia = new AtomicLong(1L);

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }
    public void criarProduto(ProdutoDTO dto) {

        if(dto.getPreco() <= 0){
            throw new IllegalArgumentException("Preço deve ser maior que zero");
        }

        Produto produto = new Produto(sequencia.getAndIncrement(), dto.getNome(), dto.getPreco());

        repository.salvar(produto);
    }

    public List<Produto> listarProdutos() {
        return repository.listar();
    }

    public void deletarProduto(Long id) {

        Produto produto = repository.buscarPorId(id);

        if(produto == null){
            throw new IllegalArgumentException("Produto não encontrado");
        }

        repository.deletar(id);
    }

    public void atualizarProduto(Long id, ProdutoDTO dto) {

        Produto produto = repository.buscarPorId(id);

        if(produto == null){
            throw new IllegalArgumentException("Produto não encontrado");
        }

        Produto produtoAtualizado = new Produto(id, dto.getNome(), dto.getPreco());

        repository.atualizar(id, produtoAtualizado);
    }
}
