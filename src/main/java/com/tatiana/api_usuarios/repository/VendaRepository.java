package com.tatiana.api_usuarios.repository;

import com.tatiana.api_usuarios.model.Venda;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class VendaRepository {

    private final List<Venda> vendas = new ArrayList<Venda>();

    public void salvar(Venda venda) {
        vendas.add(venda);
    }

    public List<Venda> listar() {
        return vendas;
    }
    public Venda buscarPorId(Long id) {
        for (Venda venda : vendas) {
            if (venda.getId() != null && venda.getId().equals(id)) {
                return venda;
            }
        }
        return null;
    }

}
