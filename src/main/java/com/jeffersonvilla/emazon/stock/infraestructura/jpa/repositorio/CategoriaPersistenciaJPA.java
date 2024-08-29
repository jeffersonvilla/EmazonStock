package com.jeffersonvilla.emazon.stock.infraestructura.jpa.repositorio;

import com.jeffersonvilla.emazon.stock.dominio.modelo.Categoria;
import com.jeffersonvilla.emazon.stock.dominio.spi.ICategoriaPersistenciaPort;
import com.jeffersonvilla.emazon.stock.infraestructura.jpa.mapper.CategoriaMapperJPA;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.ORDEN_ASCENDENTE;
import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.SORT_NOMBRE;

@RequiredArgsConstructor
public class CategoriaPersistenciaJPA implements ICategoriaPersistenciaPort {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapperJPA mapper;

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
        Sort sort =  (orden.equals(ORDEN_ASCENDENTE)) ?
                Sort.by(SORT_NOMBRE).ascending() : Sort.by(SORT_NOMBRE).descending();
        Pageable pageable = PageRequest.of(pagina, tamano, sort);
        return categoriaRepository.findAll(pageable).map(mapper::categoriaEntityToCategoria).toList();
    }

}
