package com.jeffersonvilla.emazon.stock.infraestructura.jpa.mapper;

import com.jeffersonvilla.emazon.stock.dominio.modelo.Categoria;
import com.jeffersonvilla.emazon.stock.infraestructura.jpa.entidad.CategoriaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoriaMapperJpa {

    CategoriaEntity categoriaToCategoriaEntity(Categoria categoria);
    Categoria categoriaEntityToCategoria(CategoriaEntity entity);
}
