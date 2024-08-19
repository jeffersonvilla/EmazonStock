package com.jeffersonvilla.emazon.stock.dominio.util;

public class MensajesErrorCategoria extends MensajesErrorGenerales{
    protected MensajesErrorCategoria(){ throw new AssertionError(); }

    public static final String NOMBRE_TAMANO_MAXIMO = "El nombre debe tener máximo 50 caracteres";

    public static final String DESCRIPCION_TAMANO_MAXIMO = "La descripcion debe tener máximo 90 caracteres";
}
