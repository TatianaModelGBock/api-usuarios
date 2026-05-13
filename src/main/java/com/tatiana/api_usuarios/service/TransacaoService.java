package com.tatiana.api_usuarios.service;

import com.tatiana.api_usuarios.dto.TransacaoDTO;
import com.tatiana.api_usuarios.model.Transacao;
import com.tatiana.api_usuarios.repository.TransacaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransacaoService {

    private final TransacaoRepository repository;

    public TransacaoService(TransacaoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Transacao criarTransacao(TransacaoDTO dto) {

        Optional<Transacao> jaExiste = repository.findByChaveIdempotencia(dto.getChaveIdempotencia());

        if (jaExiste.isPresent()) {
            throw new IllegalArgumentException("Erro: Esta transação já foi cadastrada");
        }

        Transacao transacao = new Transacao();
        transacao.setVendaId(dto.getVendaId());
        transacao.setValor(dto.getValor());
        transacao.setChaveIdempotencia(dto.getChaveIdempotencia());

        return repository.save(transacao);
    }

    @Transactional
    public Transacao confirmarPagamento(Long id){
        Transacao transacao = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transação não encontrada"));

        transacao.confirmar();

        return repository.save(transacao);
    }
}
