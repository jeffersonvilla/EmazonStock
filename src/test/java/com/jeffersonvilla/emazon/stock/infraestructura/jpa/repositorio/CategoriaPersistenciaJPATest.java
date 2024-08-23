package com.jeffersonvilla.emazon.stock.infraestructura.jpa.repositorio;

import com.jeffersonvilla.emazon.stock.dominio.modelo.Categoria;
import com.jeffersonvilla.emazon.stock.infraestructura.jpa.entidad.CategoriaEntity;
import com.jeffersonvilla.emazon.stock.infraestructura.jpa.mapper.CategoriaMapperJPA;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.jeffersonvilla.emazon.stock.infraestructura.jpa.repositorio.CategoriaPersistenciaJPA.SORT_NOMBRE;
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

    @Test
    void testListarCategoriasOrdenAscendentePorNombre() {
        int pagina = 0;
        int tamano = 10;

        CategoriaEntity categoriaEntity1 = mock(CategoriaEntity.class);
        CategoriaEntity categoriaEntity2 = mock(CategoriaEntity.class);
        List<CategoriaEntity> categoriaEntities = Arrays.asList(categoriaEntity1, categoriaEntity2);

        Page<CategoriaEntity> page = new PageImpl<>(categoriaEntities);

        Categoria categoria1 = mock(Categoria.class);
        Categoria categoria2 = mock(Categoria.class);

        when(categoriaRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(mapper.categoriaEntityToCategoria(categoriaEntity1)).thenReturn(categoria1);
        when(mapper.categoriaEntityToCategoria(categoriaEntity2)).thenReturn(categoria2);


        List<Categoria> resultado = categoriaPersistenciaJPA
                .listarCategoriasOrdenAscendentePorNombre(pagina, tamano);


        assertEquals(2, resultado.size());
        assertEquals(categoria1, resultado.get(0));
        assertEquals(categoria2, resultado.get(1));

        Pageable expectedPageable = PageRequest.of(pagina, tamano, Sort.by(SORT_NOMBRE).ascending());
        verify(categoriaRepository, times(1)).findAll(expectedPageable);

        verify(mapper, times(1)).categoriaEntityToCategoria(categoriaEntity1);
        verify(mapper, times(1)).categoriaEntityToCategoria(categoriaEntity2);
    }

    @Test
    void testListarCategoriasOrdenDescendentePorNombre() {
        int pagina = 0;
        int tamano = 10;

        CategoriaEntity categoriaEntity1 = mock(CategoriaEntity.class);
        CategoriaEntity categoriaEntity2 = mock(CategoriaEntity.class);
        List<CategoriaEntity> categoriaEntities = Arrays.asList(categoriaEntity1, categoriaEntity2);

        Page<CategoriaEntity> page = new PageImpl<>(categoriaEntities);

        Categoria categoria1 = mock(Categoria.class);
        Categoria categoria2 = mock(Categoria.class);

        when(categoriaRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(mapper.categoriaEntityToCategoria(categoriaEntity1)).thenReturn(categoria1);
        when(mapper.categoriaEntityToCategoria(categoriaEntity2)).thenReturn(categoria2);


        List<Categoria> resultado = categoriaPersistenciaJPA
                .listarCategoriasOrdenDescendentePorNombre(pagina, tamano);


        assertEquals(2, resultado.size());
        assertEquals(categoria1, resultado.get(0));
        assertEquals(categoria2, resultado.get(1));

        Pageable expectedPageable = PageRequest.of(pagina, tamano, Sort.by(SORT_NOMBRE).descending());
        verify(categoriaRepository, times(1)).findAll(expectedPageable);

        verify(mapper, times(1)).categoriaEntityToCategoria(categoriaEntity1);
        verify(mapper, times(1)).categoriaEntityToCategoria(categoriaEntity2);
    }

}
