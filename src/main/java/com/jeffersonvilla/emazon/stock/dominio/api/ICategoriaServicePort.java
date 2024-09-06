package com.jeffersonvilla.emazon.stock.dominio.api;

import com.jeffersonvilla.emazon.stock.dominio.modelo.Categoria;

import java.util.List;

public interface ICategoriaServicePort {

    Categoria crearCategoria(Categoria categoria);

    List<Categoria> listarCategoria(int pagina, int tamano, String orden);

    Categoria obtenerCategoriaPorId(Long id);
}
