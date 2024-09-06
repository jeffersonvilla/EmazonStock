package com.jeffersonvilla.emazon.stock.dominio.excepciones;

public class NoEncontradoException extends RuntimeException {
    public NoEncontradoException(String message) {
        super(message);
    }
}
