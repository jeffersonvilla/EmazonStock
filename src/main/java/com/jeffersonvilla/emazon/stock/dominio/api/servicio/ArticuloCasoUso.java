package com.jeffersonvilla.emazon.stock.dominio.api.servicio;

import com.jeffersonvilla.emazon.stock.dominio.api.IArticuloServicePort;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.articulo.CreacionArticuloException;
import com.jeffersonvilla.emazon.stock.dominio.modelo.Articulo;
import com.jeffersonvilla.emazon.stock.dominio.spi.IArticuloPersistenciaPort;

import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.CANTIDAD_MAXIMA_CATEGORIAS_POR_ARTICULO;
import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.CANTIDAD_MINIMA_CATEGORIAS_POR_ARTICULO;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorArticulo.CANTIDAD_MAXIMA_CATEGORIAS;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorArticulo.CANTIDAD_MINIMA_CATEGORIAS;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorMarca.MARCA_NULO;
import static com.jeffersonvilla.emazon.stock.dominio.util.ValidacionCamposModelo.*;

public class ArticuloCasoUso implements IArticuloServicePort {

    private final IArticuloPersistenciaPort persistencia;

    public ArticuloCasoUso(IArticuloPersistenciaPort persistencia){
        this.persistencia = persistencia;
    }

    @Override
    public Articulo crearArticulo(Articulo articulo) {

        realizarValidaciones(articulo);

        if(articulo.getCategorias().size() < CANTIDAD_MINIMA_CATEGORIAS_POR_ARTICULO){
            throw new CreacionArticuloException(CANTIDAD_MINIMA_CATEGORIAS);
        }
        if(articulo.getCategorias().size() > CANTIDAD_MAXIMA_CATEGORIAS_POR_ARTICULO){
            throw new CreacionArticuloException(CANTIDAD_MAXIMA_CATEGORIAS);
        }

        return persistencia.crearArticulo(articulo);
    }

    private void realizarValidaciones(Articulo articulo){
        validarNombreNoNuloNiVacio(articulo.getNombre());
        validarDescripcionNoNuloNiVacio(articulo.getDescripcion());
        validarCantidadNoNuloNiNegativo(articulo.getCantidad());
        validarPrecioNoNuloNiNegativo(articulo.getPrecio());

        if(articulo.getMarca() == null){
            throw new CreacionArticuloException(MARCA_NULO);
        }
    }
}
