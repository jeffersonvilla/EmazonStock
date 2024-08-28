package com.jeffersonvilla.emazon.stock.dominio.util;

import com.jeffersonvilla.emazon.stock.dominio.excepciones.DescripcionNoValidaException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.NombreNoValidoException;

import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorGenerales.*;

public class ValidacionCamposModelo {

    private ValidacionCamposModelo(){
        throw new IllegalStateException("Clase utilitaria");
    }

    public static void validarNombreNoNuloNiVacio(String nombre){
        if(nombre == null){
            throw new NombreNoValidoException(NOMBRE_NULL);
        }
        if(nombre.isBlank()){
            throw new NombreNoValidoException(NOMBRE_VACIO);
        }
    }

    public static void validarDescripcionNoNuloNiVacio(String descripcion){
        if(descripcion == null){
            throw new DescripcionNoValidaException(DESCRIPCION_NULL);
        }
        if(descripcion.isBlank()){
            throw new DescripcionNoValidaException(DESCRIPCION_VACIA);
        }
    }
}
