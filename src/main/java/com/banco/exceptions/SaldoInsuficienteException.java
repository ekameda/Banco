package com.banco.exceptions;

//@ResponseStatus(HttpStatus.NOT_FOUND)
public class SaldoInsuficienteException extends RuntimeException {

  public SaldoInsuficienteException(String message) {
      super(message);
  }
}