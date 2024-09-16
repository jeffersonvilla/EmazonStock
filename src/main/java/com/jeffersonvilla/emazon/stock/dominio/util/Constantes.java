package com.jeffersonvilla.emazon.stock.dominio.util;

public class Constantes {

    private Constantes(){
        throw new IllegalStateException("Clase utilitaria");
    }

    public static final String ORDEN_ASCENDENTE = "ASC";
    public static final String ORDEN_DESCENDENTE = "DESC";

    public static final int PAGINA_MINIMO = 0;
    public static final int TAMANO_MINIMO = 1;

    public static final int TAMANO_MAXIMO_NOMBRE_CATEGORIA = 50;
    public static final int TAMANO_MAXIMO_DESCRIPCION_CATEGORIA = 90;

    public static final int TAMANO_MAXIMO_NOMBRE_MARCA = 50;
    public static final int TAMANO_MAXIMO_DESCRIPCION_MARCA = 120;

    public static final int TAMANO_MAXIMO_NOMBRE_ARTICULO = 50;
    public static final int TAMANO_MAXIMO_DESCRIPCION_ARTICULO = 120;

    public static final int CANTIDAD_MINIMA_CATEGORIAS_POR_ARTICULO = 1;
    public static final int CANTIDAD_MAXIMA_CATEGORIAS_POR_ARTICULO = 3;

    public static final int CANTIDAD_STOCK_MINIMA = 0;
    public static final int PRECIO_MINIMO = 0;
    public static final int CANTIDAD_AUMENTO_MINIMO_CANTIDAD_STOCK = 1;

    public static final String SORT_NOMBRE = "nombre";

    public static final String LISTAR_POR_ARTICULO = "articulo";
    public static final String LISTAR_POR_MARCA = "marca";
    public static final String LISTAR_POR_CATEGORIA = "categorias";

    public static final String ROL_ADMIN = "admin";
    public static final String ROL_AUX_BODEGA = "aux_bodega";

}
