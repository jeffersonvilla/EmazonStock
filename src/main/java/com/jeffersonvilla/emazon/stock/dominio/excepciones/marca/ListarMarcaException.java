package com.jeffersonvilla.emazon.stock.dominio.excepciones.marca;

import com.jeffersonvilla.emazon.stock.dominio.excepciones.BadRequestException;

public class ListarMarcaException extends BadRequestException {
    public ListarMarcaException(String message) {
        super(message);
    }
}
