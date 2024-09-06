package com.jeffersonvilla.emazon.stock.dominio.excepciones.categoria;

import com.jeffersonvilla.emazon.stock.dominio.excepciones.BadRequestException;

public class ListarCategoriaException extends BadRequestException {
    public ListarCategoriaException(String message) {
        super(message);
    }
}
