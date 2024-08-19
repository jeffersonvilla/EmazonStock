package com.jeffersonvilla.emazon.stock.infraestructura.jpa.repositorio;

import com.jeffersonvilla.emazon.stock.dominio.modelo.Categoria;
import com.jeffersonvilla.emazon.stock.infraestructura.jpa.entidad.CategoriaEntity;
import com.jeffersonvilla.emazon.stock.infraestructura.jpa.mapper.CategoriaMapperJPA;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoriaPersistenciaJPATest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private CategoriaMapperJPA mapper;

    @InjectMocks
    private CategoriaPersistenciaJPA categoriaPersistenciaJPA;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearCategoria() {
        Categoria categoria = new Categoria(1L, "Electrónica",
                "Categoría de dispositivos electrónicos");
        CategoriaEntity categoriaEntity = new CategoriaEntity();
        when(mapper.categoriaToCategoriaEntity(categoria)).thenReturn(categoriaEntity);
        when(categoriaRepository.save(categoriaEntity)).thenReturn(categoriaEntity);
        when(mapper.categoriaEntityToCategoria(categoriaEntity)).thenReturn(categoria);

        Categoria resultado = categoriaPersistenciaJPA.crearCategoria(categoria);

        assertEquals(categoria, resultado);
        verify(mapper, times(1)).categoriaToCategoriaEntity(categoria);
        verify(categoriaRepository, times(1)).save(categoriaEntity);
        verify(mapper, times(1)).categoriaEntityToCategoria(categoriaEntity);
    }

    @Test
    void testObtenerCategoriaPorNombreCuandoExiste() {
        String nombre = "Electrónica";
        CategoriaEntity categoriaEntity = new CategoriaEntity();
        Categoria categoria = new Categoria(1L, nombre,
                "Categoría de dispositivos electrónicos");

        when(categoriaRepository.findByNombre(nombre)).thenReturn(Optional.of(categoriaEntity));
        when(mapper.categoriaEntityToCategoria(categoriaEntity)).thenReturn(categoria);

        Optional<Categoria> resultado = categoriaPersistenciaJPA.obtenerCategoriaPorNombre(nombre);

        assertTrue(resultado.isPresent());
        assertEquals(categoria, resultado.get());
        verify(categoriaRepository, times(1)).findByNombre(nombre);
        verify(mapper, times(1)).categoriaEntityToCategoria(categoriaEntity);
    }

    @Test
    void testObtenerCategoriaPorNombreCuandoNoExiste() {
        String nombre = "Electrónica";
        when(categoriaRepository.findByNombre(nombre)).thenReturn(Optional.empty());

        Optional<Categoria> resultado = categoriaPersistenciaJPA.obtenerCategoriaPorNombre(nombre);

        assertFalse(resultado.isPresent());
        verify(categoriaRepository, times(1)).findByNombre(nombre);
        verify(mapper, never()).categoriaEntityToCategoria(any());
    }
}
