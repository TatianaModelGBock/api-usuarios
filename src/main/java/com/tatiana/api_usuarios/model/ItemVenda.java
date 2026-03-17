package com.tatiana.api_usuarios.model;

public class ItemVenda {

    private Long produtoId;
    private String nomeProduto;
    private double valorUnitario;
    private int quantidade;
    private double valorTotal;


    public ItemVenda() {
    }

    public ItemVenda(Long produtoId, String nomeProduto, double valorUnitario, int quantidade) {
        this.produtoId = produtoId;
        this.nomeProduto = nomeProduto;
        this.valorUnitario = valorUnitario;
        this.quantidade = quantidade;
        this.valorTotal = valorUnitario * quantidade;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public void setValorUnitario(double valorUnitario) {
        if(valorUnitario < 0){
            throw new IllegalArgumentException("Preço não pode ser negativo");
        }
        this.valorUnitario = valorUnitario;
        this.valorTotal = this.valorUnitario * this.quantidade;
    }

    public void setQuantidade(int quantidade) {
        if(quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }
        this.quantidade = quantidade;
        this.valorTotal = this.valorUnitario * this.quantidade;
    }
}
