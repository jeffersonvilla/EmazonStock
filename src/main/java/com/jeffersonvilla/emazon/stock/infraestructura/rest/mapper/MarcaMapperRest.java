package com.jeffersonvilla.emazon.stock.infraestructura.rest.mapper;

import com.jeffersonvilla.emazon.stock.dominio.modelo.Marca;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.marca.CrearMarcaRequestDto;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.marca.CrearMarcaResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MarcaMapperRest {

    @Mapping(target = "id", ignore = true)
    public Marca crearMarcaRequestDtoToMarca(CrearMarcaRequestDto dto);
    public CrearMarcaResponseDto marcaToCrearMarcaResponseDto(Marca marca);
}
