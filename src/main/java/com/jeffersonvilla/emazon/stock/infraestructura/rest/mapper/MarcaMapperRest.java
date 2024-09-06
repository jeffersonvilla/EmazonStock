package com.jeffersonvilla.emazon.stock.infraestructura.rest.mapper;

import com.jeffersonvilla.emazon.stock.dominio.modelo.Marca;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.marca.CrearMarcaRequestDto;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.marca.CrearMarcaResponseDto;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.marca.ListarMarcaResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MarcaMapperRest {

    @Mapping(target = "id", ignore = true)
    Marca crearMarcaRequestDtoToMarca(CrearMarcaRequestDto dto);
    CrearMarcaResponseDto marcaToCrearMarcaResponseDto(Marca marca);
    ListarMarcaResponseDto marcaToListarMarcaResponseDto(Marca marca);
}
