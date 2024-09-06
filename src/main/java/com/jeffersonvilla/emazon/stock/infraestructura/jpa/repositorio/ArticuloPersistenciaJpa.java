package com.jeffersonvilla.emazon.stock.infraestructura.jpa.repositorio;

import com.jeffersonvilla.emazon.stock.dominio.modelo.Articulo;
import com.jeffersonvilla.emazon.stock.dominio.spi.IArticuloPersistenciaPort;
import com.jeffersonvilla.emazon.stock.infraestructura.jpa.mapper.ArticuloMapperJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.LISTAR_POR_ARTICULO;
import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.SORT_NOMBRE;

@RequiredArgsConstructor
public class ArticuloPersistenciaJpa implements IArticuloPersistenciaPort {

    private final ArticuloRepository articuloRepository;
    private final ArticuloMapperJpa mapper;

    @Override
    public Articulo crearArticulo(Articulo articulo) {

        return mapper.articuloEntityToArticulo(
                articuloRepository.save(
                        mapper.articuloToArticuloEntity(articulo)
                )
        );
    }

    @Override
    public List<Articulo> listarArticulos(int pagina, int tamano, String orden, String listarPor) {
        String atributo = (listarPor.equals(LISTAR_POR_ARTICULO))? "" : (listarPor + ".");
        Sort sort = Sort.by(Sort.Direction.fromString(orden), atributo + SORT_NOMBRE);
        Pageable pageable = PageRequest.of(pagina, tamano, sort);
        return articuloRepository.findAll(pageable).map(mapper::articuloEntityToArticulo).toList();
    }

}
