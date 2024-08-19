package com.jeffersonvilla.emazon.stock.dominio.util;

public class MensajesErrorGenerales {

    protected MensajesErrorGenerales(){ throw new AssertionError(); }

    public static final String NOMBRE_VACIO = "El nombre debe tener caractéres legibles";

    public static final String NOMBRE_NULL = "Debe tener un nombre";

    public static final String NOMBRE_NO_DISPONIBLE = "El nombre elegido no esta disponible";

    public static final String DESCRIPCION_VACIA = "La descripción debe tener caractéres legibles";

    public static final String DESCRIPCION_NULL = "Debe tener una descripción";

}
