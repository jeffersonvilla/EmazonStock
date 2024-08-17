package com.jeffersonvilla.emazon.stock.infraestructura.jpa.mapper;

import com.jeffersonvilla.emazon.stock.dominio.modelo.Categoria;
import com.jeffersonvilla.emazon.stock.infraestructura.jpa.entidad.CategoriaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoriaMapperJPA {

    public CategoriaEntity categoriaToCategoriaEntity(Categoria categoria);
    public Categoria categoriaEntityToCategoria(CategoriaEntity entity);
}
