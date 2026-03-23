package com.tatiana.api_usuarios.service;

import com.tatiana.api_usuarios.dto.AdicionarItemDTO;
import com.tatiana.api_usuarios.dto.FecharVendaDTO;
import com.tatiana.api_usuarios.model.ItemVenda;
import com.tatiana.api_usuarios.model.Produto;
import com.tatiana.api_usuarios.model.Venda;
import com.tatiana.api_usuarios.repository.ProdutoRepository;
import com.tatiana.api_usuarios.repository.VendaRepository;
import org.springframework.stereotype.Service;

@Service
public class CaixaService {

    private final VendaRepository vendaRepository;
    private final ProdutoRepository produtoRepository;

    public CaixaService(VendaRepository vendaRepository,
                        ProdutoRepository produtoRepository) {
        this.vendaRepository = vendaRepository;
        this.produtoRepository = produtoRepository;
    }

    public Venda iniciarVenda() {
        Venda venda = new Venda();
        return vendaRepository.save(venda);
    }

    public Venda adicionarItem(Long vendaId, AdicionarItemDTO dto) {
        Venda venda = vendaRepository.findById(vendaId)
                .orElseThrow(() -> new IllegalArgumentException("Venda não encontrada"));

        if (venda.isVendaFinalizada()) {
            throw new IllegalArgumentException("Venda finalizada");
        }

        Produto produto = produtoRepository.findById(dto.getProdutoId())
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));

        if (dto.getQuantidade() <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }

        ItemVenda item = new ItemVenda(
                produto.getId(), produto.getNome(),
                produto.getPreco(), dto.getQuantidade());

        venda.adicionarItem(item);
        return vendaRepository.save(venda);
    }

    public Venda buscarVenda(Long vendaId) {
        return vendaRepository.findById(vendaId)
                .orElseThrow(() -> new IllegalArgumentException("Venda não encontrada"));
    }

    public Venda fecharVenda(Long vendaId, FecharVendaDTO dto) {
        Venda venda = vendaRepository.findById(vendaId)
                .orElseThrow(() -> new IllegalArgumentException("Venda não encontrada"));

        if (venda.isVendaFinalizada()) {
            throw new IllegalArgumentException("Venda já finalizada");
        }

        if (dto.getValorRecebido() < venda.getTotal()) {
            throw new IllegalArgumentException("Valor recebido insuficiente");
        }

        venda.setVendaFinalizada(true);
        return vendaRepository.save(venda);
    }
}
