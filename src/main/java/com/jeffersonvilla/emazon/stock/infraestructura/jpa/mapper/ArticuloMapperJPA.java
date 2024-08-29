package com.jeffersonvilla.emazon.stock.infraestructura.jpa.mapper;

import com.jeffersonvilla.emazon.stock.dominio.modelo.Articulo;
import com.jeffersonvilla.emazon.stock.infraestructura.jpa.entidad.ArticuloEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArticuloMapperJPA {

    public Articulo articuloEntityToArticulo(ArticuloEntity entity);
    public ArticuloEntity articuloToArticuloEntity(Articulo articulo);
}
