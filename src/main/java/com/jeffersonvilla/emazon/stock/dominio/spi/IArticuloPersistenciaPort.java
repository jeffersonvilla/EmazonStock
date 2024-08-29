package com.jeffersonvilla.emazon.stock.dominio.spi;

import com.jeffersonvilla.emazon.stock.dominio.modelo.Articulo;

public interface IArticuloPersistenciaPort {

    public Articulo crearArticulo(Articulo articulo);
}
