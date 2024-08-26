package com.jeffersonvilla.emazon.stock.dominio.spi;

import com.jeffersonvilla.emazon.stock.dominio.modelo.Marca;

import java.util.Optional;

public interface IMarcaPersistenciaPort {

    public Marca crearMarca(Marca marca);
    public Optional<Marca> obtenerMarcaPorNombre(String nombre);
}
