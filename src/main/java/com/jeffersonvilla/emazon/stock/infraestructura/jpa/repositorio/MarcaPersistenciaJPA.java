package com.jeffersonvilla.emazon.stock.infraestructura.jpa.repositorio;

import com.jeffersonvilla.emazon.stock.dominio.modelo.Marca;
import com.jeffersonvilla.emazon.stock.dominio.spi.IMarcaPersistenciaPort;
import com.jeffersonvilla.emazon.stock.infraestructura.jpa.mapper.MarcaMapperJPA;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.ORDEN_ASCENDENTE;
import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.SORT_NOMBRE;

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

    @Override
    public List<Marca> listarMarcasPorNombre(int pagina, int tamano, String orden) {
        Sort sort = (orden.equals(ORDEN_ASCENDENTE))?
                Sort.by(SORT_NOMBRE).ascending() : Sort.by(SORT_NOMBRE).descending();
        Pageable pageable = PageRequest.of(pagina, tamano, sort);
        return marcaRepository.findAll(pageable).map(mapper::marcaEntityToMarca).toList();
    }

}
