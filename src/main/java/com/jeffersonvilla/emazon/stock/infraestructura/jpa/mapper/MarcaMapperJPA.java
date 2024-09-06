package com.jeffersonvilla.emazon.stock.infraestructura.jpa.mapper;

import com.jeffersonvilla.emazon.stock.dominio.modelo.Marca;
import com.jeffersonvilla.emazon.stock.infraestructura.jpa.entidad.MarcaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MarcaMapperJPA {

    MarcaEntity marcaToMarcaEntity(Marca marca);
    Marca marcaEntityToMarca(MarcaEntity entity);
}
