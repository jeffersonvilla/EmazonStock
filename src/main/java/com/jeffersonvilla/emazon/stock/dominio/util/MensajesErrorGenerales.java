package com.jeffersonvilla.emazon.stock.dominio.util;

import static com.jeffersonvilla.emazon.stock.dominio.api.servicio.CategoriaCasoUso.ORDEN_ASCENDENTE;
import static com.jeffersonvilla.emazon.stock.dominio.api.servicio.CategoriaCasoUso.ORDEN_DESCENDENTE;

public class MensajesErrorGenerales {

    protected MensajesErrorGenerales(){ throw new AssertionError(); }

    public static final String NOMBRE_VACIO = "El nombre debe tener caractéres legibles";

    public static final String NOMBRE_NULL = "Debe tener un nombre";

    public static final String NOMBRE_NO_DISPONIBLE = "El nombre elegido no esta disponible";

    public static final String DESCRIPCION_VACIA = "La descripción debe tener caractéres legibles";

    public static final String DESCRIPCION_NULL = "Debe tener una descripción";

    public static final String ORDEN_NO_VALIDO = "Ordenes disponibles son: '"
            + ORDEN_ASCENDENTE +"' y '"+ ORDEN_DESCENDENTE +"'";

    public static final String PAGINA_VALOR_MINIMO = "La pagina debe ser mayor o igual a cero.";

    public static final String TAMANO_VALOR_MINIMO = "El tamaño de la pagina debe ser mayor o igual a uno.";
}
