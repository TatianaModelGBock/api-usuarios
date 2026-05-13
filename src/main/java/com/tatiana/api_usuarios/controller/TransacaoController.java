package com.tatiana.api_usuarios.controller;

import com.tatiana.api_usuarios.dto.TransacaoDTO;
import com.tatiana.api_usuarios.model.Transacao;
import com.tatiana.api_usuarios.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/financeiro")
public class TransacaoController {

    private final TransacaoService service;

    public TransacaoController(TransacaoService service) {
        this.service = service;
    }

    @PostMapping("/pagar")
    public ResponseEntity<Transacao> criar(@RequestBody TransacaoDTO dto){
        Transacao novaTransacao = service.criarTransacao(dto);
        return ResponseEntity.ok(novaTransacao);
    }
    @PutMapping("/confirmar/{id}")
    public  ResponseEntity<Transacao> confirmar(@PathVariable Long id){
        Transacao transacaoConfirmada = service.confirmarPagamento(id);
        return ResponseEntity.ok(transacaoConfirmada);
    }

}
