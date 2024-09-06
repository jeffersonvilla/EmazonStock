package com.jeffersonvilla.emazon.stock.dominio.excepciones.general;

import com.jeffersonvilla.emazon.stock.dominio.excepciones.BadRequestException;

public class PrecioNoValidoException extends BadRequestException {
    public PrecioNoValidoException(String message) {
        super(message);
    }
}
