package com.tatiana.api_usuarios.model;

import jakarta.persistence.*;

@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private double preco;
    private int quantidadeEstoque;
    private int estoqueMinimo;


    @Version
    private Integer version;

    public Produto() {}

    public Produto(Long id, String nome, double preco, int quantidadeEstoque, int estoqueMinimo) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
        this.estoqueMinimo = estoqueMinimo;
    }
    public Integer getVersion() { return version; }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }
    public void setQuantidadeEstoque(int quantidadeEstoque) {
        if (quantidadeEstoque < 0) {
            throw new IllegalArgumentException("Estoque não pode ser negativo");
        }
        this.quantidadeEstoque = quantidadeEstoque;
    }
    public int getEstoqueMinimo() {
        return estoqueMinimo;
    }
    public void setEstoqueMinimo(int estoqueMinimo) {
        if (estoqueMinimo < 0) {
            throw new IllegalArgumentException("O estoque mínimo não pode ser negativo");
        }
        this.estoqueMinimo = estoqueMinimo;
    }
}
