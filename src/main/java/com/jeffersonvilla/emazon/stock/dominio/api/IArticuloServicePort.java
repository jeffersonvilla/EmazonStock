package com.jeffersonvilla.emazon.stock.dominio.api;

import com.jeffersonvilla.emazon.stock.dominio.modelo.Articulo;

import java.util.List;

public interface IArticuloServicePort {

    Articulo crearArticulo(Articulo articulo);

    List<Articulo> listarArticulo(int pagina, int tamano, String orden, String listarPor);

    Articulo aumentarCantidadStock(long idArticulo, int cantidad);
}
