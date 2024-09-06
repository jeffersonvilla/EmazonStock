package com.jeffersonvilla.emazon.stock.dominio.excepciones.general;

import com.jeffersonvilla.emazon.stock.dominio.excepciones.BadRequestException;

public class CantidadNoValidaException extends BadRequestException {
    public CantidadNoValidaException(String message) {
        super(message);
    }
}
