package com.tatiana.api_usuarios.service;

import com.tatiana.api_usuarios.dto.ProdutoDTO;
import com.tatiana.api_usuarios.model.Produto;
import com.tatiana.api_usuarios.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;


    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    public void criarProduto(ProdutoDTO dto) {

        if(dto.getPreco() <= 0){
            throw new IllegalArgumentException("Preço deve ser maior que zero");
        }

        Produto produto = new Produto();
        produto.setNome(dto.getNome());
        produto.setPreco(dto.getPreco());
        repository.save(produto);
    }

    public List<Produto> listarProdutos() {
        List<Produto> produtos =  repository.findAll();
        produtos.sort(Comparator.comparing(Produto::getPreco));
        return produtos;
    }

    public void deletarProduto(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Produto não encontrado");
        }
        repository.deleteById(id);
    }

    public void atualizarProduto(Long id, ProdutoDTO dto) {
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));
        produto.setNome(dto.getNome());
        produto.setPreco(dto.getPreco());
        repository.save(produto);
    }
}
