package com.jeffersonvilla.emazon.stock.dominio.excepciones.categoria;

import com.jeffersonvilla.emazon.stock.dominio.excepciones.NoEncontradoException;

public class CategoriaNoExisteException extends NoEncontradoException {
    public CategoriaNoExisteException(String message) {
        super(message);
    }
}
