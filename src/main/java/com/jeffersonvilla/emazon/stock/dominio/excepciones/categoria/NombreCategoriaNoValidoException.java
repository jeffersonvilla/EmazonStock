package com.jeffersonvilla.emazon.stock.dominio.excepciones.categoria;

public class NombreCategoriaNoValidoException extends RuntimeException{

    public NombreCategoriaNoValidoException(String message){
        super(message);
    }
}
