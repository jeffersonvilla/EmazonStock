package com.jeffersonvilla.emazon.stock.dominio.excepciones.marca;

public class NombreMarcaNoValidoException extends RuntimeException {
    public NombreMarcaNoValidoException(String message) {
        super(message);
    }
}
