package com.banco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.banco.domain.TipoTarifa;

@Repository
public interface TipoTarifaRepository extends JpaRepository<TipoTarifa, Long> {

    TipoTarifa nome(String nome);
    TipoTarifa findByNome(String nome);

    // Cheque Especial -> Es
    // find ou read ou query ou stream ou get
    TipoTarifa findByNomeContaining(String nome);

    //  @Query("select tipo From TipoTarifa tipo where tipo.nome = ?1")
    @Query("select tipo From TipoTarifa tipo where tipo.nome = :nome")
    TipoTarifa procuraPorNome(@Param("nome") String nomeDoMartin);
}