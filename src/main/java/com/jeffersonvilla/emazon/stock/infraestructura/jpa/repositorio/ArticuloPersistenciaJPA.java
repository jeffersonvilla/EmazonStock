package com.jeffersonvilla.emazon.stock.infraestructura.jpa.repositorio;

import com.jeffersonvilla.emazon.stock.dominio.modelo.Articulo;
import com.jeffersonvilla.emazon.stock.dominio.spi.IArticuloPersistenciaPort;
import com.jeffersonvilla.emazon.stock.infraestructura.jpa.mapper.ArticuloMapperJPA;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ArticuloPersistenciaJPA implements IArticuloPersistenciaPort {

    private final ArticuloRepository articuloRepository;
    private final ArticuloMapperJPA mapper;

    @Override
    public Articulo crearArticulo(Articulo articulo) {

        return mapper.articuloEntityToArticulo(
                articuloRepository.save(
                        mapper.articuloToArticuloEntity(articulo)
                )
        );
    }
}
