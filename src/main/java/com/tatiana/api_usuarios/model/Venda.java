package com.tatiana.api_usuarios.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Venda {

    private Long id;
    private LocalDateTime dataHora;
    private List<ItemVenda> itens;
    private double total;
    private boolean vendaFinalizada;


    public Venda() {
        this.dataHora = LocalDateTime.now();
        this.itens = new ArrayList<>();
        this.total = 0.0;
        this.vendaFinalizada = false;
    }

    public Venda(Long id) {
        this();
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public List<ItemVenda> getItens() {
        return itens;
    }

    public double getTotal() {
        return total;
    }

    public boolean isVendaFinalizada() {
        return vendaFinalizada;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setVendaFinalizada(boolean vendaFinalizada) {
        this.vendaFinalizada = vendaFinalizada;
    }

    public void adicionarItem(ItemVenda item) {

        if (vendaFinalizada) {
            throw new IllegalStateException("Venda já finalizada");
        }

        this.itens.add(item);
        this.total += item.getValorTotal();
    }
    public void finalizarVenda() {
        this.vendaFinalizada = true;
    }
}
