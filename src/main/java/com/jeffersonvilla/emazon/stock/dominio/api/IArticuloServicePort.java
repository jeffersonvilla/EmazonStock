package com.jeffersonvilla.emazon.stock.dominio.api;

import com.jeffersonvilla.emazon.stock.dominio.modelo.Articulo;

import java.util.List;

public interface IArticuloServicePort {

    public Articulo crearArticulo(Articulo articulo);

    public List<Articulo> listarArticulo(int pagina, int tamano, String orden, String listarPor);
}
