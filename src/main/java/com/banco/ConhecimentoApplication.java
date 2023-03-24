package com.banco;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.banco.domain.Banco;
import com.banco.repository.BancoRepository;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class ConhecimentoApplication {

	@Autowired
	private BancoRepository bancoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(ConhecimentoApplication.class, args);
		
	}
	
	@PostConstruct
	public void cadastrar() {
		Banco b = new Banco();
		b.setNome("Banck1");
		bancoRepository.save(b);
		
	}

}
