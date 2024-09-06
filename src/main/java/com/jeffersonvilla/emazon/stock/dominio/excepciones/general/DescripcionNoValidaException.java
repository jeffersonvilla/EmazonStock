package com.jeffersonvilla.emazon.stock.dominio.excepciones.general;

import com.jeffersonvilla.emazon.stock.dominio.excepciones.BadRequestException;

public class DescripcionNoValidaException extends BadRequestException {
    public DescripcionNoValidaException(String message) {
        super(message);
    }
}
