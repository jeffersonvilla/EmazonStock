package com.jeffersonvilla.emazon.stock.dominio.util;

import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.TAMANO_MAXIMO_DESCRIPCION_CATEGORIA;
import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.TAMANO_MAXIMO_NOMBRE_CATEGORIA;

public class MensajesErrorCategoria {
    protected MensajesErrorCategoria(){ throw new IllegalStateException("Clase utilitaria"); }

    public static final String NOMBRE_TAMANO_MAXIMO = "El nombre debe tener máximo "+
            TAMANO_MAXIMO_NOMBRE_CATEGORIA +" caracteres";

    public static final String DESCRIPCION_TAMANO_MAXIMO = "La descripción debe tener máximo "+
            TAMANO_MAXIMO_DESCRIPCION_CATEGORIA +" caracteres";

    public static final String CATEGORIA_POR_ID_NO_EXISTE = "La categoría que busca por id no existe";

    public static final String ID_NULO_CATEGORIA = "El ID de la categoría no puede ser nulo";
}
