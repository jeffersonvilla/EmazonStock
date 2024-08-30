package com.jeffersonvilla.emazon.stock.dominio.api.servicio;

import com.jeffersonvilla.emazon.stock.dominio.api.ICategoriaServicePort;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.categoria.CategoriaNoExisteException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.categoria.ListarCategoriaException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.general.IdNuloException;
import com.jeffersonvilla.emazon.stock.dominio.modelo.Categoria;
import com.jeffersonvilla.emazon.stock.dominio.spi.ICategoriaPersistenciaPort;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.categoria.CreacionCategoriaException;

import java.util.List;
import java.util.Optional;

import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.*;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorCategoria.*;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorGenerales.*;
import static com.jeffersonvilla.emazon.stock.dominio.util.ValidacionCamposModelo.validarDescripcionNoNuloNiVacio;
import static com.jeffersonvilla.emazon.stock.dominio.util.ValidacionCamposModelo.validarNombreNoNuloNiVacio;

public class CategoriaCasoUso implements ICategoriaServicePort {

    private final ICategoriaPersistenciaPort persistencia;

    public CategoriaCasoUso(ICategoriaPersistenciaPort persistencia) {
        this.persistencia = persistencia;
    }

    @Override
    public Categoria crearCategoria(Categoria categoria) {
        validarNombreNoNuloNiVacio(categoria.getNombre());
        validarDescripcionNoNuloNiVacio(categoria.getDescripcion());

        if(persistencia.obtenerCategoriaPorNombre(categoria.getNombre()).isPresent()){
            throw new CreacionCategoriaException(NOMBRE_NO_DISPONIBLE);
        }
        if(categoria.getNombre().length() > TAMANO_MAXIMO_NOMBRE_CATEGORIA){
            throw new CreacionCategoriaException(NOMBRE_TAMANO_MAXIMO);
        }
        if(categoria.getDescripcion().length() > TAMANO_MAXIMO_DESCRIPCION_CATEGORIA){
            throw new CreacionCategoriaException(DESCRIPCION_TAMANO_MAXIMO);
        }
        return persistencia.crearCategoria(categoria);
    }

    @Override
    public List<Categoria> listarCategoria(int pagina, int tamano, String orden) {
        if(pagina < PAGINA_MINIMO){
            throw new ListarCategoriaException(PAGINA_VALOR_MINIMO);
        }
        if(tamano < TAMANO_MINIMO){
            throw new ListarCategoriaException(TAMANO_VALOR_MINIMO);
        }
        if(!(orden.equals(ORDEN_ASCENDENTE) || orden.equals(ORDEN_DESCENDENTE))){
            throw new ListarCategoriaException(ORDENES_VALIDOS);
        }

        return persistencia.listarCategoriasPorNombre(pagina, tamano, orden);
    }

    @Override
    public Categoria obtenerCategoriaPorId(Long id) {
        if(id == null){
            throw new IdNuloException(ID_NULO_CATEGORIA);
        }

        Optional<Categoria> categoria = persistencia.obtenerCategoriaPorId(id);
        if(!categoria.isPresent()){
            throw new CategoriaNoExisteException(CATEGORIA_POR_ID_NO_EXISTE + " id: " + id);
        }

        return categoria.get();
    }
}
