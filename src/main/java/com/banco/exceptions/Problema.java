package com.banco.exceptions;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Problema {

	private LocalDateTime dataHora = LocalDateTime.now();
	private String mensagem;

	public Problema(String mensagem) {
		this.mensagem = mensagem;
	}
}