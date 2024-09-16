package com.jeffersonvilla.emazon.stock.dominio.util;

public class MensajesErrorArticulo {

    private MensajesErrorArticulo(){ throw new IllegalStateException("Clase utilitaria"); }

    public static final String CANTIDAD_MINIMA_CATEGORIAS = "El articulo debe tener mínimo "+
            Constantes.CANTIDAD_MINIMA_CATEGORIAS_POR_ARTICULO +" categoría asociada";

    public static final String CANTIDAD_MAXIMA_CATEGORIAS = "El articulo debe tener maximo "+
            Constantes.CANTIDAD_MAXIMA_CATEGORIAS_POR_ARTICULO +" categorias asociadas";

    public static final String CANTIDAD_NEGATIVA = "La cantidad no puede ser negativa ";

    public static final String PRECIO_NEGATIVO = "El precio no puede ser negativo";

    public static final String ARTICULO_NO_ENCONTRADO = "El articulo no existe";

    public static final String ERROR_AUMENTANDO_STOCK = "No se ha podido aumentar el stock del articulo";
}
