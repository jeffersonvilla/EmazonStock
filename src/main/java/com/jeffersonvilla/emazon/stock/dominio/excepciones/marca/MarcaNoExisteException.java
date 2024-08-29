package com.jeffersonvilla.emazon.stock.dominio.excepciones.marca;

public class MarcaNoExisteException extends RuntimeException {
    public MarcaNoExisteException(String message) {
        super(message);
    }
}
