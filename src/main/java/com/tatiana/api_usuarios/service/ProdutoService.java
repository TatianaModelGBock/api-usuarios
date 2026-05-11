package com.tatiana.api_usuarios.service;

import com.tatiana.api_usuarios.dto.ProdutoDTO;
import com.tatiana.api_usuarios.model.Produto;
import com.tatiana.api_usuarios.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;


    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }
    @Transactional
    public void criarProduto(ProdutoDTO dto) {

        if(dto.getPreco() <= 0){
            throw new IllegalArgumentException("Preço deve ser maior que zero");
        }
        if (repository.existsByNome(dto.getNome())) {
            throw new IllegalArgumentException("Produto já cadastrado");
        }

        Produto produto = new Produto();
        produto.setNome(dto.getNome());
        produto.setPreco(dto.getPreco());
        produto.setQuantidadeEstoque(dto.getQuantidadeEstoque());
        produto.setEstoqueMinimo(dto.getEstoqueMinimo());
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
    @Transactional
    public void atualizarProduto(Long id, ProdutoDTO dto) {
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));
        produto.setNome(dto.getNome());
        produto.setPreco(dto.getPreco());
        produto.setQuantidadeEstoque(dto.getQuantidadeEstoque());
        produto.setEstoqueMinimo(dto.getEstoqueMinimo());
        repository.save(produto);
    }
    @Transactional
    public void baixarEstoque(Long id, int quantidade) {
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));

        int novoEstoque = produto.getQuantidadeEstoque() - quantidade;
        produto.setQuantidadeEstoque(novoEstoque);


        if (novoEstoque <= produto.getEstoqueMinimo()) {
            System.out.println("LOG [ALERTA]: Estoque crítico para" + produto.getNome() +
                    ". Mínimo: " + produto.getEstoqueMinimo() +
                    "| Atual: " + novoEstoque );
        }
        repository.save(produto);
    }
}
