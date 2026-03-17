package com.tatiana.api_usuarios.dto;

public class FecharVendaDTO {

    private double valorRecebido;

    public double getValorRecebido() {
        return valorRecebido;
    }

    public void setValorRecebido(double valorRecebido) {
        if(valorRecebido <= 0){
            throw new IllegalArgumentException("Valor deve ser maior que zero");
        }
        this.valorRecebido = valorRecebido;
    }
}
