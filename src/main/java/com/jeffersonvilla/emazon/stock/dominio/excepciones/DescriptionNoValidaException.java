package com.jeffersonvilla.emazon.stock.dominio.excepciones;

public class DescriptionNoValidaException extends RuntimeException
{
    public DescriptionNoValidaException(String message) {
        super(message);
    }
}
