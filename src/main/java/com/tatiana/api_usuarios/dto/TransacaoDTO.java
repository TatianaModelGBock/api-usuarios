package com.tatiana.api_usuarios.dto;

import java.math.BigDecimal;

public class TransacaoDTO {

    private Long vendaId;
    private BigDecimal valor;
    private String chaveIdempotencia;

    public Long getVendaId() {
        return vendaId;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public String getChaveIdempotencia() {
        return chaveIdempotencia;
    }

    public void setVendaId(Long vendaId) {
        this.vendaId = vendaId;
    }

    public void setValor(BigDecimal valor) {
        if ( valor == null || valor.compareTo(BigDecimal.ZERO) == 0 ) {
            throw  new IllegalArgumentException("Valor deve ser maior que zero");
        }
        this.valor = valor;
    }

    public void setChaveIdempotencia(String chaveIdempotencia) {
        if ( chaveIdempotencia == null || chaveIdempotencia.isBlank()) {
            throw new IllegalArgumentException("Chave de idempotência é obrigatória");
        }
        this.chaveIdempotencia = chaveIdempotencia;
    }
}
