package com.jeffersonvilla.emazon.stock.dominio.util;

import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.TAMANO_MAXIMO_DESCRIPCION_CATEGORIA;
import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.TAMANO_MAXIMO_NOMBRE_CATEGORIA;

public class MensajesErrorCategoria {
    protected MensajesErrorCategoria(){ throw new IllegalStateException("Clase utilitaria"); }

    public static final String NOMBRE_TAMANO_MAXIMO = "El nombre debe tener máximo "+
            TAMANO_MAXIMO_NOMBRE_CATEGORIA +" caracteres";

    public static final String DESCRIPCION_TAMANO_MAXIMO = "La descripción debe tener máximo "+
            TAMANO_MAXIMO_DESCRIPCION_CATEGORIA +" caracteres";
}
