package com.jeffersonvilla.emazon.stock.dominio.excepciones.marca;

import com.jeffersonvilla.emazon.stock.dominio.excepciones.BadRequestException;

public class CreacionMarcaException extends BadRequestException {
    public CreacionMarcaException(String message) {
        super(message);
    }
}
