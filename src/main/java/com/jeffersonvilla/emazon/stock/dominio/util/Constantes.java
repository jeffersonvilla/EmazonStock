package com.jeffersonvilla.emazon.stock.dominio.util;

public class Constantes {

    private Constantes(){
        throw new IllegalStateException("Clase utilitaria");
    }

    public static final String ORDEN_ASCENDENTE = "ASC";
    public static final String ORDEN_DESCENDENTE = "DES";

    public static final int TAMANO_MAXIMO_NOMBRE_CATEGORIA = 50;
    public static final int TAMANO_MAXIMO_DESCRIPCION_CATEGORIA = 90;

    public static final int TAMANO_MAXIMO_NOMBRE_MARCA = 50;
    public static final int TAMANO_MAXIMO_DESCRIPCION_MARCA = 120;

}
