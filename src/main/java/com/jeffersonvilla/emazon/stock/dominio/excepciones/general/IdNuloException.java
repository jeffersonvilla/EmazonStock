package com.jeffersonvilla.emazon.stock.dominio.excepciones.general;

import com.jeffersonvilla.emazon.stock.dominio.excepciones.BadRequestException;

public class IdNuloException extends BadRequestException {
    public IdNuloException(String message) {
        super(message);
    }
}
