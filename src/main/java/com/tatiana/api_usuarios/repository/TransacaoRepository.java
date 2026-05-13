package com.tatiana.api_usuarios.repository;

import com.tatiana.api_usuarios.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {


    Optional<Transacao> findByChaveIdempotencia(String chaveIdempotencia);

    List<Transacao> findByVendaId(Long vendaId);
}