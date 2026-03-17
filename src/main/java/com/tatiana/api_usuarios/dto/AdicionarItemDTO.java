package com.tatiana.api_usuarios.dto;

public class AdicionarItemDTO {

    private Long produtoId;
    private int quantidade;

    public Long getProdutoId() {
        return produtoId;
    }

    public int getQuantidade() {
        return quantidade;
    }
    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public void setQuantidade(int quantidade) {

        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }

        this.quantidade = quantidade;
    }
}
