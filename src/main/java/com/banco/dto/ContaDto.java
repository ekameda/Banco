package com.banco.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContaDto {

	private int agencia;
	private int numero;
	private BigDecimal saldo;
}