package com.jeffersonvilla.emazon.stock.dominio.spi;

import com.jeffersonvilla.emazon.stock.dominio.modelo.Marca;

import java.util.List;
import java.util.Optional;

public interface IMarcaPersistenciaPort {

    Marca crearMarca(Marca marca);
    Optional<Marca> obtenerMarcaPorNombre(String nombre);
    Optional<Marca> obtenerMarcaPorId(Long id);
    List<Marca> listarMarcasPorNombre(int pagina, int tamano, String orden);
}
