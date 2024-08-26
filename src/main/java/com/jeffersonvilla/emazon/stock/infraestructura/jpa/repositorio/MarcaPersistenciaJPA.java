package com.jeffersonvilla.emazon.stock.infraestructura.jpa.repositorio;

import com.jeffersonvilla.emazon.stock.dominio.modelo.Marca;
import com.jeffersonvilla.emazon.stock.dominio.spi.IMarcaPersistenciaPort;
import com.jeffersonvilla.emazon.stock.infraestructura.jpa.mapper.MarcaMapperJPA;

import java.util.Optional;

public class MarcaPersistenciaJPA implements IMarcaPersistenciaPort {

    private final MarcaRepository marcaRepository;
    private final MarcaMapperJPA mapper;

    public MarcaPersistenciaJPA(MarcaRepository marcaRepository, MarcaMapperJPA mapper) {
        this.marcaRepository = marcaRepository;
        this.mapper = mapper;
    }

    @Override
    public Marca crearMarca(Marca marca) {
        return mapper.marcaEntityToMarca(
                marcaRepository.save(
                        mapper.marcaToMarcaEntity(marca)
                )
        );
    }

    @Override
    public Optional<Marca> obtenerMarcaPorNombre(String nombre) {
        return marcaRepository.findByNombre(nombre)
                .map(mapper::marcaEntityToMarca);
    }
}
