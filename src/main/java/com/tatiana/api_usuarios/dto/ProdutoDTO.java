package com.tatiana.api_usuarios.dto;

public class ProdutoDTO {

    private String nome;
    private double preco;
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
        this.preco = preco;
    }
}
