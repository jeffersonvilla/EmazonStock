package com.jeffersonvilla.emazon.stock.infraestructura.rest.mapper;

import com.jeffersonvilla.emazon.stock.dominio.modelo.Categoria;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.CrearCategoriaRequestDto;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.CrearCategoriaResponseDto;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.ListarCategoriaResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoriaMapperRest {

    @Mapping(target = "id", ignore = true)
    public Categoria crearCategoriaRequestDtoToCategoria(CrearCategoriaRequestDto dto);
    public CrearCategoriaResponseDto categoriaToCrearCategoriaResponseDto(Categoria categoria);
    public ListarCategoriaResponseDto categoriaToListarCategoriaResponseDto(Categoria categoria);
}
