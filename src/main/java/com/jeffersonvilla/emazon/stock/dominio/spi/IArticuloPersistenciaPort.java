package com.jeffersonvilla.emazon.stock.dominio.spi;

import com.jeffersonvilla.emazon.stock.dominio.modelo.Articulo;

import java.util.List;

public interface IArticuloPersistenciaPort {

    Articulo crearArticulo(Articulo articulo);
    List<Articulo> listarArticulos(int pagina, int tamano, String orden, String listarPor);

}
