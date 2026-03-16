package com.tatiana.api_usuarios.controller;

import com.tatiana.api_usuarios.dto.AdicionarItemDTO;
import com.tatiana.api_usuarios.dto.FecharVendaDTO;
import com.tatiana.api_usuarios.model.Venda;
import com.tatiana.api_usuarios.service.CaixaService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/caixa")
public class CaixaController {

    private final CaixaService caixaService;

    public CaixaController(CaixaService caixaService) {
        this.caixaService = caixaService;
    }

    @PostMapping("/vendas")
    public Venda iniciarVenda() {
        return caixaService.iniciarVenda();
    }

    @PostMapping("/vendas/{id}/itens")
    public Venda adicionarVenda(@PathVariable Long id, @RequestBody AdicionarItemDTO dto) {
        return caixaService.adicionarItem(id, dto);
    }


    @GetMapping("/vendas/{id}")
    public Venda buscarVenda(@PathVariable Long id) {
        return caixaService.buscarVenda(id);
    }

    @PostMapping("/vendas/{id}/fechar")
    public Venda finalizarVenda(@PathVariable Long id, @RequestBody FecharVendaDTO dto) {
        return caixaService.fecharVenda(id, dto);
    }
}
