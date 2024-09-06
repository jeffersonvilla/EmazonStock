package com.jeffersonvilla.emazon.stock.infraestructura.rest.mapper;

import com.jeffersonvilla.emazon.stock.dominio.modelo.Articulo;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.articulo.CrearArticuloRequestDto;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.articulo.CrearArticuloResponseDto;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.articulo.ListarArticuloResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ArticuloMapperRest {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "marca", ignore = true)
    @Mapping(target = "categorias", ignore = true)
    Articulo crearArticuloRequestDtoToArticulo(CrearArticuloRequestDto dto);
    CrearArticuloResponseDto articuloToCrearArticuloResponseDto(Articulo articulo);
    ListarArticuloResponseDto articuloToListarArticuloResponseDto(Articulo articulo);
}
