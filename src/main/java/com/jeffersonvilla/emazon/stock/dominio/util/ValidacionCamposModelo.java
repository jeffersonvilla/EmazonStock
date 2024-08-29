package com.jeffersonvilla.emazon.stock.dominio.util;

import com.jeffersonvilla.emazon.stock.dominio.excepciones.general.CantidadNoValidaException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.general.DescripcionNoValidaException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.general.NombreNoValidoException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.general.PrecioNoValidoException;

import java.math.BigDecimal;

import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.CANTIDAD_STOCK_MINIMA;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorArticulo.CANTIDAD_NEGATIVA;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorArticulo.PRECIO_NEGATIVO;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorGenerales.*;

public class ValidacionCamposModelo {

    //Comparar un valor n1 de tipo BigDecimal con otro valor n2
    //Da como resultado -1 cuando n1 es menor que n2
    private static final int VALOR_ES_MENOR = 0;

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

    public static void validarCantidadNoNuloNiNegativo(Integer cantidad){
        if(cantidad == null){
            throw new CantidadNoValidaException(CANTIDAD_NULL);
        }
        if(cantidad < CANTIDAD_STOCK_MINIMA){
            throw new CantidadNoValidaException(CANTIDAD_NEGATIVA);
        }
    }

    public static void validarPrecioNoNuloNiNegativo(BigDecimal precio){
        if(precio == null){
            throw new PrecioNoValidoException(PRECIO_NULL);
        }
        if(precio.compareTo(BigDecimal.ZERO) < VALOR_ES_MENOR){
            throw new PrecioNoValidoException(PRECIO_NEGATIVO);
        }
    }
}
