package com.banco.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banco.domain.Banco;

public interface BancoRepository extends JpaRepository<Banco, Long> {

    Optional<Banco> findByCodigo(int codigo);
}