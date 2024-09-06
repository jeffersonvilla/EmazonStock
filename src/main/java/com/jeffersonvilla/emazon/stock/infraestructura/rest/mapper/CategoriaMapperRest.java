package com.jeffersonvilla.emazon.stock.infraestructura.rest.mapper;

import com.jeffersonvilla.emazon.stock.dominio.modelo.Categoria;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.categoria.CrearCategoriaRequestDto;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.categoria.CrearCategoriaResponseDto;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.categoria.ListarCategoriaResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoriaMapperRest {

    @Mapping(target = "id", ignore = true)
    Categoria crearCategoriaRequestDtoToCategoria(CrearCategoriaRequestDto dto);
    CrearCategoriaResponseDto categoriaToCrearCategoriaResponseDto(Categoria categoria);
    ListarCategoriaResponseDto categoriaToListarCategoriaResponseDto(Categoria categoria);
}
