package com.tatiana.api_usuarios.service;

import com.tatiana.api_usuarios.dto.AdicionarItemDTO;
import com.tatiana.api_usuarios.dto.FecharVendaDTO;
import com.tatiana.api_usuarios.model.ItemVenda;
import com.tatiana.api_usuarios.model.Produto;
import com.tatiana.api_usuarios.model.Venda;
import com.tatiana.api_usuarios.repository.ProdutoRepository;
import com.tatiana.api_usuarios.repository.VendaRepository;
import org.springframework.stereotype.Service;


import java.util.concurrent.atomic.AtomicLong;

@Service
public class CaixaService {

    private final VendaRepository vendaRepository;
    private final ProdutoRepository produtoRepository;
    private final AtomicLong sequencia = new AtomicLong(1L);

    public CaixaService(VendaRepository vendaRepository, ProdutoRepository produtoRepository) {
        this.vendaRepository = vendaRepository;
        this.produtoRepository = produtoRepository;
    }

    //inicia uma venda vazia
    public Venda iniciarVenda() {
        Long novoId = sequencia.getAndIncrement();
        Venda venda = new Venda(novoId);
        vendaRepository.salvar(venda);
        return venda;
    }

    //add um item a venda
    public Venda adicionarItem(Long vendaId, AdicionarItemDTO dto)
    {
        Venda venda = vendaRepository.buscarPorId(vendaId);
        if (venda == null) {
            throw new IllegalArgumentException("Venda não encontrada");
        }
        if (venda.isVendaFinalizada()) {
            throw new IllegalArgumentException("Venda finalizada");
        }
        Produto produto = produtoRepository.buscarPorId(dto.getProdutoId());
        if (produto == null) {
            throw new IllegalArgumentException("Produto não encontrada");
        }
        if (dto.getQuantidade() <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }

        //usando o Produto.getPreco()
        ItemVenda item = new ItemVenda(produto.getId(), produto.getNome(), produto.getPreco(), dto.getQuantidade());
        venda.getItens().add(item);
        venda.adicionarAoTotalItens(item.getValorTotal());
        return venda;
    }
    // buscar detalhes da venda
    public Venda buscarVenda(Long vendaId) {
        Venda venda = vendaRepository.buscarPorId(vendaId);
        if (venda == null) {
            throw new IllegalArgumentException("Venda não encontrada");
        }
        return venda;
    }
    //fechar a venda(receberpagamento e calcular o troco)
    public Venda fecharVenda(Long vendaId, FecharVendaDTO dto) {
        Venda venda = vendaRepository.buscarPorId(vendaId);
        if (venda.isVendaFinalizada()) {
            throw new IllegalArgumentException("Venda finalizada");
        }
        double total = venda.getTotal();
        double recebido = dto.getValorRecebido();
        if (recebido < total) {
            throw new IllegalArgumentException("Valor recebido insuficiente");
        }
        venda.setVendaFinalizada(true);
        return venda;
    }

}
