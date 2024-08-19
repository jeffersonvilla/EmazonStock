package com.jeffersonvilla.emazon.stock.dominio.spi;

import com.jeffersonvilla.emazon.stock.dominio.modelo.Categoria;

import java.util.Optional;

public interface ICategoriaPersistenciaPort {

    public Categoria crearCategoria(Categoria categoria);
    public Optional<Categoria> obtenerCategoriaPorNombre(String nombre);
}
