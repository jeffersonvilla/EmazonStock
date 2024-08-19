package com.jeffersonvilla.emazon.stock.infraestructura.jpa.repositorio;

import com.jeffersonvilla.emazon.stock.dominio.modelo.Categoria;
import com.jeffersonvilla.emazon.stock.dominio.spi.ICategoriaPersistenciaPort;
import com.jeffersonvilla.emazon.stock.infraestructura.jpa.mapper.CategoriaMapperJPA;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

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

}
