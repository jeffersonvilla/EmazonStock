package com.jeffersonvilla.emazon.stock.infraestructura.jpa.mapper;

import com.jeffersonvilla.emazon.stock.dominio.modelo.Articulo;
import com.jeffersonvilla.emazon.stock.infraestructura.jpa.entidad.ArticuloEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArticuloMapperJpa {

    default Articulo articuloEntityToArticulo(ArticuloEntity entity){
        return articuloEntityToArticuloBuilder(entity).build();
    }
    ArticuloEntity articuloToArticuloEntity(Articulo articulo);

    Articulo.ArticuloBuilder articuloEntityToArticuloBuilder(ArticuloEntity entity);
}
