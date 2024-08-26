package com.jeffersonvilla.emazon.stock.infraestructura.jpa.repositorio;

import com.jeffersonvilla.emazon.stock.dominio.modelo.Marca;
import com.jeffersonvilla.emazon.stock.infraestructura.jpa.entidad.MarcaEntity;
import com.jeffersonvilla.emazon.stock.infraestructura.jpa.mapper.MarcaMapperJPA;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MarcaPersistenciaJPATest {

    @Mock
    private MarcaRepository marcaRepository;

    @Mock
    private MarcaMapperJPA mapper;

    @InjectMocks
    private MarcaPersistenciaJPA marcaPersistenciaJPA;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearMarca() {
        Marca marca = new Marca(1L, "Marca A",
                "Marca de dispositivos electrónicos");
        MarcaEntity marcaEntity = new MarcaEntity();
        when(mapper.marcaToMarcaEntity(marca)).thenReturn(marcaEntity);
        when(marcaRepository.save(marcaEntity)).thenReturn(marcaEntity);
        when(mapper.marcaEntityToMarca(marcaEntity)).thenReturn(marca);

        Marca resultado = marcaPersistenciaJPA.crearMarca(marca);

        assertEquals(marca, resultado);
        verify(mapper, times(1)).marcaToMarcaEntity(marca);
        verify(marcaRepository, times(1)).save(marcaEntity);
        verify(mapper, times(1)).marcaEntityToMarca(marcaEntity);
    }

    @Test
    void testObtenerMarcaPorNombreCuandoExiste() {
        String nombre = "Marca A";
        MarcaEntity marcaEntity = mock(MarcaEntity.class);
        Marca marca = new Marca(1L, nombre,
                "Marca de dispositivos electrónicos");

        when(marcaRepository.findByNombre(nombre)).thenReturn(Optional.of(marcaEntity));
        when(mapper.marcaEntityToMarca(marcaEntity)).thenReturn(marca);

        Optional<Marca> resultado = marcaPersistenciaJPA.obtenerMarcaPorNombre(nombre);

        assertTrue(resultado.isPresent());
        assertEquals(marca, resultado.get());
        verify(marcaRepository, times(1)).findByNombre(nombre);
        verify(mapper, times(1)).marcaEntityToMarca(marcaEntity);
    }

    @Test
    void testObtenerMarcaPorNombreCuandoNoExiste() {
        String nombre = "Marca A";
        when(marcaRepository.findByNombre(nombre)).thenReturn(Optional.empty());

        Optional<Marca> resultado = marcaPersistenciaJPA.obtenerMarcaPorNombre(nombre);

        assertFalse(resultado.isPresent());
        verify(marcaRepository, times(1)).findByNombre(nombre);
        verify(mapper, never()).marcaEntityToMarca(any());
    }
}