package com.jeffersonvilla.emazon.stock.infraestructura.jpa.repositorio;

import com.jeffersonvilla.emazon.stock.dominio.modelo.Articulo;
import com.jeffersonvilla.emazon.stock.infraestructura.jpa.entidad.ArticuloEntity;
import com.jeffersonvilla.emazon.stock.infraestructura.jpa.mapper.ArticuloMapperJPA;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ArticuloPersistenciaJPATest {

    @Mock
    private ArticuloRepository articuloRepository;

    @Mock
    private ArticuloMapperJPA mapper;

    @InjectMocks
    private ArticuloPersistenciaJPA articuloPersistenciaJPA;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearArticulo() {
        Articulo articulo = mock(Articulo.class);
        ArticuloEntity articuloEntity = mock(ArticuloEntity.class);
        when(mapper.articuloToArticuloEntity(articulo)).thenReturn(articuloEntity);
        when(articuloRepository.save(articuloEntity)).thenReturn(articuloEntity);
        when(mapper.articuloEntityToArticulo(articuloEntity)).thenReturn(articulo);

        Articulo resultado = articuloPersistenciaJPA.crearArticulo(articulo);

        assertEquals(articulo, resultado);
        verify(mapper, times(1)).articuloToArticuloEntity(articulo);
        verify(articuloRepository, times(1)).save(articuloEntity);
        verify(mapper, times(1)).articuloEntityToArticulo(articuloEntity);
    }
}