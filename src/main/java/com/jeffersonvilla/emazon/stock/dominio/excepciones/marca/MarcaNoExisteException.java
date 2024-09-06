package com.jeffersonvilla.emazon.stock.dominio.excepciones.marca;

import com.jeffersonvilla.emazon.stock.dominio.excepciones.NoEncontradoException;

public class MarcaNoExisteException extends NoEncontradoException {
    public MarcaNoExisteException(String message) {
        super(message);
    }
}
