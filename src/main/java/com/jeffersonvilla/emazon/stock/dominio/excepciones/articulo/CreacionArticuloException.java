package com.jeffersonvilla.emazon.stock.dominio.excepciones.articulo;

import com.jeffersonvilla.emazon.stock.dominio.excepciones.BadRequestException;

public class CreacionArticuloException extends BadRequestException {
    public CreacionArticuloException(String message) {
        super(message);
    }
}
