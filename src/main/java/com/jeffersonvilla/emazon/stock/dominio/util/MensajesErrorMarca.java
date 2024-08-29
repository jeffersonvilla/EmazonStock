package com.jeffersonvilla.emazon.stock.dominio.util;

import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.TAMANO_MAXIMO_DESCRIPCION_MARCA;
import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.TAMANO_MAXIMO_NOMBRE_MARCA;

public class MensajesErrorMarca {

    private MensajesErrorMarca(){ throw new IllegalStateException("Clase utilitaria"); }

    public static final String NOMBRE_TAMANO_MAXIMO = "El nombre debe tener máximo "+
            TAMANO_MAXIMO_NOMBRE_MARCA +" caracteres";

    public static final String DESCRIPCION_TAMANO_MAXIMO = "La descripción debe tener máximo "+
            TAMANO_MAXIMO_DESCRIPCION_MARCA +" caracteres";

    public static final String MARCA_POR_ID_NO_EXISTE = "La marca que busca por id no existe";

    public static final String MARCA_NULO = "El articulo debe tener una marca";

    public static final String ID_NULO_MARCA = "El ID de la marca no puede ser nulo";
}
