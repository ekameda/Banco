package com.banco.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banco.domain.Titular;

public interface TitularRepository extends JpaRepository<Titular, Long> {

    // select * from conta where titular_id = parametro
	// List<Conta> findByTitularId(Long id);
}