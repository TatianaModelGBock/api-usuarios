package com.tatiana.api_usuarios.dto;

public class ProdutoDTO {

    private String nome;
    private double preco;
    private int quantidadeEstoque;
    private int estoqueMinimo;


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }
    public void setPreco(double preco) {
        if(preco < 0) {
            throw new IllegalArgumentException("Preço não pode ser negativo");
        }
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
