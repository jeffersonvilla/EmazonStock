package com.jeffersonvilla.emazon.stock.infraestructura.jpa.repositorio;

import com.jeffersonvilla.emazon.stock.dominio.modelo.Articulo;
import com.jeffersonvilla.emazon.stock.infraestructura.jpa.entidad.ArticuloEntity;
import com.jeffersonvilla.emazon.stock.infraestructura.jpa.mapper.ArticuloMapperJPA;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ArticuloPersistenciaJPATest {

    @Mock
    private ArticuloRepository articuloRepository;

    @Mock
    private ArticuloMapperJPA mapper;

    @InjectMocks
    private ArticuloPersistenciaJPA articuloPersistenciaJPA;

    private ArticuloEntity articuloEntity1;
    private ArticuloEntity articuloEntity2;
    private Articulo articulo1;
    private Articulo articulo2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        articuloEntity1 = mock(ArticuloEntity.class);
        articuloEntity2 = mock(ArticuloEntity.class);
        articulo1 = mock(Articulo.class);
        articulo2 = mock(Articulo.class);
    }

    @Test
    void testCrearArticulo() {
        ArticuloEntity articuloEntity = mock(ArticuloEntity.class);
        when(mapper.articuloToArticuloEntity(articulo1)).thenReturn(articuloEntity);
        when(articuloRepository.save(articuloEntity)).thenReturn(articuloEntity);
        when(mapper.articuloEntityToArticulo(articuloEntity)).thenReturn(articulo1);

        Articulo resultado = articuloPersistenciaJPA.crearArticulo(articulo1);

        assertEquals(articulo1, resultado);
        verify(mapper, times(1)).articuloToArticuloEntity(articulo1);
        verify(articuloRepository, times(1)).save(articuloEntity);
        verify(mapper, times(1)).articuloEntityToArticulo(articuloEntity);
    }

    @Test
    void testListarArticulosConExito() {
        List<ArticuloEntity> articuloEntities = List.of(articuloEntity1, articuloEntity2);
        Page<ArticuloEntity> articuloPage = new PageImpl<>(articuloEntities);
        when(articuloRepository.findAll(any(Pageable.class))).thenReturn(articuloPage);
        when(mapper.articuloEntityToArticulo(articuloEntity1)).thenReturn(articulo1);
        when(mapper.articuloEntityToArticulo(articuloEntity2)).thenReturn(articulo2);

        List<Articulo> resultado = articuloPersistenciaJPA
                .listarArticulos(0, 10, ORDEN_ASCENDENTE, LISTAR_POR_ARTICULO);

        assertEquals(2, resultado.size());
        assertEquals(articulo1, resultado.get(0));
        assertEquals(articulo2, resultado.get(1));

        verify(articuloRepository, times(1))
                .findAll(any(Pageable.class));
        verify(mapper, times(2))
                .articuloEntityToArticulo(any(ArticuloEntity.class));
    }

    @Test
    void testListarArticulosSinResultados() {
        when(articuloRepository.findAll(any(Pageable.class))).thenReturn(Page.empty());

        List<Articulo> resultado = articuloPersistenciaJPA
                .listarArticulos(0, 10, ORDEN_ASCENDENTE, LISTAR_POR_CATEGORIA);

        assertTrue(resultado.isEmpty());

        verify(articuloRepository, times(1))
                .findAll(any(Pageable.class));
        verify(mapper, never())
                .articuloEntityToArticulo(any(ArticuloEntity.class));
    }
}