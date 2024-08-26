package com.jeffersonvilla.emazon.stock.dominio.api.servicio;

import com.jeffersonvilla.emazon.stock.dominio.api.IMarcaServicePort;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.marca.CreacionMarcaException;
import com.jeffersonvilla.emazon.stock.dominio.modelo.Marca;
import com.jeffersonvilla.emazon.stock.dominio.spi.IMarcaPersistenciaPort;

import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.TAMANO_MAXIMO_DESCRIPCION_MARCA;
import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.TAMANO_MAXIMO_NOMBRE_MARCA;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorGenerales.NOMBRE_NO_DISPONIBLE;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorMarca.NOMBRE_TAMANO_MAXIMO;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorMarca.DESCRIPCION_TAMANO_MAXIMO;

public class MarcaCasoUso implements IMarcaServicePort {

    private final IMarcaPersistenciaPort persistencia;

    public MarcaCasoUso(IMarcaPersistenciaPort persistencia){
        this.persistencia = persistencia;
    }

    @Override
    public Marca crearMarca(Marca marca) {
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
}
