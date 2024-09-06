package com.jeffersonvilla.emazon.stock.dominio.excepciones.articulo;

import com.jeffersonvilla.emazon.stock.dominio.excepciones.BadRequestException;

public class ListarArticuloException extends BadRequestException {
    public ListarArticuloException(String message) {
        super(message);
    }
}
