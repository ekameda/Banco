package com.banco.exceptions;

public class BancoInexistenteException extends RuntimeException{

    public BancoInexistenteException(String message) {
        super(message);
    }
}