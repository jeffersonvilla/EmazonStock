package com.jeffersonvilla.emazon.stock.dominio.spi;

import com.jeffersonvilla.emazon.stock.dominio.modelo.Categoria;

import java.util.List;
import java.util.Optional;

public interface ICategoriaPersistenciaPort {

    Categoria crearCategoria(Categoria categoria);
    Optional<Categoria> obtenerCategoriaPorNombre(String nombre);
    Optional<Categoria> obtenerCategoriaPorId(Long id);
    List<Categoria> listarCategoriasPorNombre(int pagina, int tamano, String orden);
}
