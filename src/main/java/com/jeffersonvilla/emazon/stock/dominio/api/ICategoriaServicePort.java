package com.jeffersonvilla.emazon.stock.dominio.api;

import com.jeffersonvilla.emazon.stock.dominio.modelo.Categoria;

import java.util.List;

public interface ICategoriaServicePort {

    public Categoria crearCategoria(Categoria categoria);

    public List<Categoria> listarCategoria(int pagina, int tamano, String orden);

    public Categoria obtenerCategoriaPorId(Long id);
}
