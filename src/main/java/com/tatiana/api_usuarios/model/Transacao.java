package com.tatiana.api_usuarios.model;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long vendaId;
    private BigDecimal valor;
    private String chaveIdempotencia;
    private LocalDateTime dataHora;

    @Enumerated(EnumType.STRING)
    private StatusTransacao status;


    @Version
    private Integer version;

    public Transacao() {
        this.dataHora = LocalDateTime.now();
        this.status = StatusTransacao.PENDENTE;
    }

    public Long getId() {
        return id;
    }

    public Long getVendaId() {
        return vendaId;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public String getChaveIdempotencia() {
        return chaveIdempotencia;
    }

    public StatusTransacao getStatus() {
        return status;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVendaId(Long vendaId) {
        this.vendaId = vendaId;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public void setChaveIdempotencia(String chaveIdempotencia) {
        this.chaveIdempotencia = chaveIdempotencia;
    }

    public void confirmar(){
        this.status = StatusTransacao.CONCLUIDA;
    }

}
