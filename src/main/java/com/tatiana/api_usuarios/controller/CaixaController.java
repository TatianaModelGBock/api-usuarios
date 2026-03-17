package com.tatiana.api_usuarios.controller;

import com.tatiana.api_usuarios.dto.AdicionarItemDTO;
import com.tatiana.api_usuarios.dto.FecharVendaDTO;
import com.tatiana.api_usuarios.model.Venda;
import com.tatiana.api_usuarios.service.CaixaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/caixa")
public class CaixaController {

    private final CaixaService caixaService;

    public CaixaController(CaixaService caixaService) {
        this.caixaService = caixaService;
    }

    @PostMapping("/vendas")
    public ResponseEntity<Venda> iniciarVenda() {
        return ResponseEntity.ok(caixaService.iniciarVenda());
    }

    @PostMapping("/vendas/{id}/itens")
    public ResponseEntity<Venda> adicionarItem(@PathVariable Long id, @RequestBody AdicionarItemDTO dto) {
        return ResponseEntity.ok(caixaService.adicionarItem(id, dto));
    }

    @GetMapping("/vendas/{id}")
    public ResponseEntity<Venda> buscarVenda(@PathVariable Long id) {
        return ResponseEntity.ok(caixaService.buscarVenda(id));
    }

    @PostMapping("/vendas/{id}/fechar")
    public ResponseEntity<Venda> finalizarVenda(@PathVariable Long id, @RequestBody FecharVendaDTO dto) {
        return ResponseEntity.ok(caixaService.fecharVenda(id, dto));
    }
}
