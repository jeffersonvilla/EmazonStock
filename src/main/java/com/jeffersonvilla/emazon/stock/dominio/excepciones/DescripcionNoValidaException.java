package com.jeffersonvilla.emazon.stock.dominio.excepciones;

public class DescripcionNoValidaException extends RuntimeException {
    public DescripcionNoValidaException(String message) {
        super(message);
    }
}
