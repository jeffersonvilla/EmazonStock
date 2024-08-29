package com.jeffersonvilla.emazon.stock.dominio.api.servicio;

import com.jeffersonvilla.emazon.stock.dominio.api.IMarcaServicePort;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.general.IdNuloException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.marca.MarcaNoExisteException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.marca.ListarMarcaException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.marca.CreacionMarcaException;
import com.jeffersonvilla.emazon.stock.dominio.modelo.Marca;
import com.jeffersonvilla.emazon.stock.dominio.spi.IMarcaPersistenciaPort;

import java.util.List;
import java.util.Optional;

import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.*;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorGenerales.*;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorGenerales.ORDEN_NO_VALIDO;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorMarca.*;
import static com.jeffersonvilla.emazon.stock.dominio.util.ValidacionCamposModelo.validarDescripcionNoNuloNiVacio;
import static com.jeffersonvilla.emazon.stock.dominio.util.ValidacionCamposModelo.validarNombreNoNuloNiVacio;

public class MarcaCasoUso implements IMarcaServicePort {

    private final IMarcaPersistenciaPort persistencia;

    public MarcaCasoUso(IMarcaPersistenciaPort persistencia){
        this.persistencia = persistencia;
    }

    @Override
    public Marca crearMarca(Marca marca) {
        validarNombreNoNuloNiVacio(marca.getNombre());
        validarDescripcionNoNuloNiVacio(marca.getDescripcion());

        if(persistencia.obtenerMarcaPorNombre(marca.getNombre()).isPresent()){
            throw new CreacionMarcaException(NOMBRE_NO_DISPONIBLE);
        }
        if(marca.getNombre().length() > TAMANO_MAXIMO_NOMBRE_MARCA){
            throw new CreacionMarcaException(NOMBRE_TAMANO_MAXIMO);
        }
        if(marca.getDescripcion().length() > TAMANO_MAXIMO_DESCRIPCION_MARCA){
            throw new CreacionMarcaException(DESCRIPCION_TAMANO_MAXIMO);
        }
        return persistencia.crearMarca(marca);
    }

    @Override
    public List<Marca> listarMarca(int pagina, int tamano, String orden) {
        if(pagina < PAGINA_MINIMO){
            throw new ListarMarcaException(PAGINA_VALOR_MINIMO);
        }
        if(tamano < TAMANO_MINIMO){
            throw new ListarMarcaException(TAMANO_VALOR_MINIMO);
        }
        if(!(orden.equals(ORDEN_ASCENDENTE) || orden.equals(ORDEN_DESCENDENTE))){
            throw new ListarMarcaException(ORDEN_NO_VALIDO);
        }

        return persistencia.listarMarcasPorNombre(pagina, tamano, orden);
    }

    @Override
    public Marca obtenerMarcaPorId(Long id) {
        if(id == null){
            throw new IdNuloException(ID_NULO_MARCA);
        }

        Optional<Marca> marca = persistencia.obtenerMarcaPorId(id);
        if(!marca.isPresent()){
            throw new MarcaNoExisteException(MARCA_POR_ID_NO_EXISTE);
        }

        return marca.get();
    }
}
