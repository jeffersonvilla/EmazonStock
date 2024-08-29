package com.jeffersonvilla.emazon.stock.dominio.excepciones.categoria;

public class CategoriaNoExisteException extends RuntimeException {
    public CategoriaNoExisteException(String message) {
        super(message);
    }
}
