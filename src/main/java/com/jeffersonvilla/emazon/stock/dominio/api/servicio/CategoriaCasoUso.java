package com.jeffersonvilla.emazon.stock.dominio.api.servicio;

import com.jeffersonvilla.emazon.stock.dominio.api.ICategoriaServicePort;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.categoria.ListarCategoriaException;
import com.jeffersonvilla.emazon.stock.dominio.modelo.Categoria;
import com.jeffersonvilla.emazon.stock.dominio.spi.ICategoriaPersistenciaPort;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.categoria.CreacionCategoriaException;

import java.util.List;

import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.*;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorCategoria.DESCRIPCION_TAMANO_MAXIMO;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorCategoria.NOMBRE_TAMANO_MAXIMO;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorGenerales.*;

public class CategoriaCasoUso implements ICategoriaServicePort {

    private static final int PAGINA_MINIMO = 0;
    private static final int TAMANO_MINIMO = 1;

    private final ICategoriaPersistenciaPort persistencia;

    public CategoriaCasoUso(ICategoriaPersistenciaPort persistencia) {
        this.persistencia = persistencia;
    }

    @Override
    public Categoria crearCategoria(Categoria categoria) {
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
            throw new ListarCategoriaException(ORDEN_NO_VALIDO);
        }

        if(orden.equals(ORDEN_ASCENDENTE)){
            return persistencia.listarCategoriasOrdenAscendentePorNombre(pagina, tamano);
        }
        return persistencia.listarCategoriasOrdenDescendentePorNombre(pagina, tamano);
    }
}
