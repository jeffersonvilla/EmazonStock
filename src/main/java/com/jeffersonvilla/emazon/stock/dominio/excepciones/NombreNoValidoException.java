package com.jeffersonvilla.emazon.stock.dominio.excepciones;

public class NombreNoValidoException extends RuntimeException {
    public NombreNoValidoException(String message) {
        super(message);
    }
}
