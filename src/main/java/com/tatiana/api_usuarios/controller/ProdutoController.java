package com.tatiana.api_usuarios.controller;

import com.tatiana.api_usuarios.dto.ProdutoDTO;
import com.tatiana.api_usuarios.model.Produto;
import com.tatiana.api_usuarios.service.ProdutoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> criarProduto(@RequestBody ProdutoDTO dto) {
        service.criarProduto(dto);
        return ResponseEntity.ok("Produto criado com sucesso");
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listarProdutos() {
        return ResponseEntity.ok(service.listarProdutos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarProduto(@PathVariable Long id, @RequestBody ProdutoDTO dto) {
        service.atualizarProduto(id, dto);
        return ResponseEntity.ok("Produto atualizado com sucesso");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarProduto(@PathVariable Long id) {
        service.deletarProduto(id);
        return ResponseEntity.ok("Produto deletado com sucesso");
    }
}
