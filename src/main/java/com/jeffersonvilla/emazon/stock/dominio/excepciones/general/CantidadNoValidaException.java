package com.jeffersonvilla.emazon.stock.dominio.excepciones.general;

public class CantidadNoValidaException extends RuntimeException {
    public CantidadNoValidaException(String message) {
        super(message);
    }
}
