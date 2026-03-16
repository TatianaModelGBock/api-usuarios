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
        this.quantidade = quantidade;
    }
}
