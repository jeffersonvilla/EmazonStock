package com.jeffersonvilla.emazon.stock.infraestructura.jpa.repositorio;

import com.jeffersonvilla.emazon.stock.dominio.modelo.Categoria;
import com.jeffersonvilla.emazon.stock.dominio.spi.ICategoriaPersistenciaPort;
import com.jeffersonvilla.emazon.stock.infraestructura.jpa.mapper.CategoriaMapperJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.SORT_NOMBRE;

@RequiredArgsConstructor
public class CategoriaPersistenciaJpa implements ICategoriaPersistenciaPort {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapperJpa mapper;

    @Override
    public Categoria crearCategoria(Categoria categoria) {
        return mapper.categoriaEntityToCategoria(
                categoriaRepository.save(
                        mapper.categoriaToCategoriaEntity(categoria)
                )
        );
    }

    @Override
    public Optional<Categoria> obtenerCategoriaPorNombre(String nombre) {
        return categoriaRepository.findByNombre(nombre)
                .map(mapper::categoriaEntityToCategoria);
    }

    @Override
    public Optional<Categoria> obtenerCategoriaPorId(Long id) {
        return categoriaRepository.findById(id)
                .map(mapper::categoriaEntityToCategoria);
    }

    @Override
    public List<Categoria> listarCategoriasPorNombre(int pagina, int tamano, String orden) {
        Sort sort = Sort.by(Sort.Direction.fromString(orden), SORT_NOMBRE);
        Pageable pageable = PageRequest.of(pagina, tamano, sort);
        return categoriaRepository.findAll(pageable).map(mapper::categoriaEntityToCategoria).toList();
    }

}
