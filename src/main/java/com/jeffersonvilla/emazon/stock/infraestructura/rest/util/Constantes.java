package com.jeffersonvilla.emazon.stock.infraestructura.rest.util;

import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.LISTAR_POR_ARTICULO;
import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.ORDEN_ASCENDENTE;

public class Constantes {

    private Constantes(){
        throw new IllegalStateException("Clase utilitaria");
    }

    public static final String PAGINA_DEFAULT = "0";
    public static final String TAMANO_DEFAULT = "10";
    public static final String ORDEN_DEFAULT = ORDEN_ASCENDENTE;
    public static final String LISTAR_POR_DEFAULT = LISTAR_POR_ARTICULO;
}
