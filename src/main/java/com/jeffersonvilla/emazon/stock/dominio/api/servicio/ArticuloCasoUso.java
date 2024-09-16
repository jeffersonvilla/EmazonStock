package com.jeffersonvilla.emazon.stock.dominio.api.servicio;

import com.jeffersonvilla.emazon.stock.dominio.api.IArticuloServicePort;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.NoEncontradoException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.articulo.ActualizacionArticuloException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.articulo.CreacionArticuloException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.articulo.ListarArticuloException;
import com.jeffersonvilla.emazon.stock.dominio.modelo.Articulo;
import com.jeffersonvilla.emazon.stock.dominio.spi.IArticuloPersistenciaPort;

import java.util.List;
import java.util.Optional;

import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.CANTIDAD_MAXIMA_CATEGORIAS_POR_ARTICULO;
import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.CANTIDAD_MINIMA_CATEGORIAS_POR_ARTICULO;
import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.LISTAR_POR_ARTICULO;
import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.LISTAR_POR_CATEGORIA;
import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.LISTAR_POR_MARCA;
import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.ORDEN_ASCENDENTE;
import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.ORDEN_DESCENDENTE;
import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.PAGINA_MINIMO;
import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.TAMANO_MINIMO;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorArticulo.ARTICULO_NO_ENCONTRADO;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorArticulo.CANTIDAD_MAXIMA_CATEGORIAS;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorArticulo.CANTIDAD_MINIMA_CATEGORIAS;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorArticulo.ERROR_AUMENTANDO_STOCK;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorGenerales.ATRIBUTOS_PARA_LISTAR;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorGenerales.ORDENES_VALIDOS;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorGenerales.PAGINA_VALOR_MINIMO;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorGenerales.TAMANO_VALOR_MINIMO;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorMarca.MARCA_NULO;
import static com.jeffersonvilla.emazon.stock.dominio.util.ValidacionCamposModelo.validarCantidadNoNuloNiNegativo;
import static com.jeffersonvilla.emazon.stock.dominio.util.ValidacionCamposModelo.validarDescripcionNoNuloNiVacio;
import static com.jeffersonvilla.emazon.stock.dominio.util.ValidacionCamposModelo.validarNombreNoNuloNiVacio;
import static com.jeffersonvilla.emazon.stock.dominio.util.ValidacionCamposModelo.validarPrecioNoNuloNiNegativo;

public class ArticuloCasoUso implements IArticuloServicePort {

    private static final int INICIO_CICLO = 0;
    private static final int FINAL_CICLO = 2;

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

    @Override
    public List<Articulo> listarArticulo(int pagina, int tamano, String orden, String listarPor) {
        if(pagina < PAGINA_MINIMO){
            throw new ListarArticuloException(PAGINA_VALOR_MINIMO);
        }
        if(tamano < TAMANO_MINIMO){
            throw new ListarArticuloException(TAMANO_VALOR_MINIMO);
        }
        if(!(orden.equals(ORDEN_ASCENDENTE) || orden.equals(ORDEN_DESCENDENTE))){
            throw new ListarArticuloException(ORDENES_VALIDOS);
        }

        if( !(listarPor.equals(LISTAR_POR_MARCA) || listarPor.equals(LISTAR_POR_CATEGORIA)
                || listarPor.equals(LISTAR_POR_ARTICULO)) ){
            throw new ListarArticuloException(ATRIBUTOS_PARA_LISTAR);
        }

        return persistencia.listarArticulos(pagina, tamano, orden, listarPor);
    }

    @Override
    public Articulo aumentarCantidadStock(long idArticulo, int cantidad) {

        for(int i = INICIO_CICLO; i <= FINAL_CICLO; i++) {

            Optional<Articulo> articuloEncontrado = persistencia.obtenerArticuloPorId(idArticulo);

            if (articuloEncontrado.isEmpty()) {
                throw new NoEncontradoException(ARTICULO_NO_ENCONTRADO);
            }

            Articulo articulo = articuloEncontrado.get();

            articulo.aumentarCantidad(cantidad);

            try {

                return persistencia.actualizarCantidadStock(articulo);

            } catch (RuntimeException ex){
                if(i == FINAL_CICLO) break;
            }
        }
        throw new ActualizacionArticuloException(ERROR_AUMENTANDO_STOCK);
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
