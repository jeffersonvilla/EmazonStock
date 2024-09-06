package com.jeffersonvilla.emazon.stock.dominio.excepciones.general;

import com.jeffersonvilla.emazon.stock.dominio.excepciones.BadRequestException;

public class NombreNoValidoException extends BadRequestException {
    public NombreNoValidoException(String message) {
        super(message);
    }
}
