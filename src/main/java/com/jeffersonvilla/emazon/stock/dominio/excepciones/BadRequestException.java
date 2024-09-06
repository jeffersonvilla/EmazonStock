package com.jeffersonvilla.emazon.stock.dominio.excepciones;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
