package com.jeffersonvilla.emazon.stock.dominio.spi;

import com.jeffersonvilla.emazon.stock.dominio.modelo.Categoria;

import java.util.List;
import java.util.Optional;

public interface ICategoriaPersistenciaPort {

    public Categoria crearCategoria(Categoria categoria);
    public Optional<Categoria> obtenerCategoriaPorNombre(String nombre);
    public List<Categoria> listarCategoriasOrdenAscendentePorNombre(int pagina, int tamano);
    public List<Categoria> listarCategoriasOrdenDescendentePorNombre(int pagina, int tamano);
}
