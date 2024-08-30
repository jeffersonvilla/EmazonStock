package com.jeffersonvilla.emazon.stock.dominio.util;

import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.*;

public class MensajesErrorGenerales {

    protected MensajesErrorGenerales(){ throw new IllegalStateException("Clase utilitaria"); }

    public static final String NOMBRE_VACIO = "El nombre debe tener caractéres legibles";

    public static final String NOMBRE_NULL = "Debe tener un nombre";

    public static final String NOMBRE_NO_DISPONIBLE = "El nombre elegido no esta disponible";

    public static final String DESCRIPCION_VACIA = "La descripción debe tener caractéres legibles";

    public static final String DESCRIPCION_NULL = "Debe tener una descripción";

    public static final String CANTIDAD_NULL = "Debe tener una cantidad";

    public static final String PRECIO_NULL = "Debe tener un precio";

    public static final String ORDENES_VALIDOS = "Ordenes disponibles son: '"
            + ORDEN_ASCENDENTE +"' y '"+ ORDEN_DESCENDENTE +"'";

    public static final String PAGINA_VALOR_MINIMO = "La pagina debe ser mayor o igual a cero.";

    public static final String TAMANO_VALOR_MINIMO = "El tamaño de la pagina debe ser mayor o igual a uno.";

    public static final String ATRIBUTOS_PARA_LISTAR = "Los atributos para listar disponibles son: '"
            + LISTAR_POR_ARTICULO + "', '" + LISTAR_POR_MARCA +"' y '" + LISTAR_POR_CATEGORIA + "'";
}
