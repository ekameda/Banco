package com.banco.exceptions;

//@ResponseStatus(HttpStatus.NOT_FOUND)
public class ContaInexistenteException extends RuntimeException {

  public ContaInexistenteException(String message) {
      super(message);
  }
}