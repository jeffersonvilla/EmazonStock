package com.jeffersonvilla.emazon.stock.dominio.excepciones.general;

public class PrecioNoValidoException extends RuntimeException {
    public PrecioNoValidoException(String message) {
        super(message);
    }
}
