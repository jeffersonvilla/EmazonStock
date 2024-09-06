package com.jeffersonvilla.emazon.stock.infraestructura.jpa.repositorio;

import com.jeffersonvilla.emazon.stock.dominio.modelo.Marca;
import com.jeffersonvilla.emazon.stock.infraestructura.jpa.entidad.MarcaEntity;
import com.jeffersonvilla.emazon.stock.infraestructura.jpa.mapper.MarcaMapperJPA;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.ORDEN_ASCENDENTE;
import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.ORDEN_DESCENDENTE;
import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.SORT_NOMBRE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    @Test
    void testListarMarcasOrdenAscendentePorNombre() {
        int pagina = 0;
        int tamano = 10;

        MarcaEntity marcaEntity1 = mock(MarcaEntity.class);
        MarcaEntity marcaEntity2 = mock(MarcaEntity.class);
        List<MarcaEntity> marcaEntities = Arrays.asList(marcaEntity1, marcaEntity2);

        Page<MarcaEntity> page = new PageImpl<>(marcaEntities);

        Marca marca1 = mock(Marca.class);
        Marca marca2 = mock(Marca.class);

        when(marcaRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(mapper.marcaEntityToMarca(marcaEntity1)).thenReturn(marca1);
        when(mapper.marcaEntityToMarca(marcaEntity2)).thenReturn(marca2);


        List<Marca> resultado = marcaPersistenciaJPA
                .listarMarcasPorNombre(pagina, tamano, ORDEN_ASCENDENTE);


        assertEquals(2, resultado.size());
        assertEquals(marca1, resultado.get(0));
        assertEquals(marca2, resultado.get(1));

        Pageable expectedPageable = PageRequest.of(pagina, tamano, Sort.by(SORT_NOMBRE).ascending());
        verify(marcaRepository, times(1)).findAll(expectedPageable);

        verify(mapper, times(1)).marcaEntityToMarca(marcaEntity1);
        verify(mapper, times(1)).marcaEntityToMarca(marcaEntity2);
    }

    @Test
    void testListarMarcasOrdenDescendentePorNombre() {
        int pagina = 0;
        int tamano = 10;

        MarcaEntity marcaEntity1 = mock(MarcaEntity.class);
        MarcaEntity marcaEntity2 = mock(MarcaEntity.class);
        List<MarcaEntity> marcaEntities = Arrays.asList(marcaEntity1, marcaEntity2);

        Page<MarcaEntity> page = new PageImpl<>(marcaEntities);

        Marca marca1 = mock(Marca.class);
        Marca marca2 = mock(Marca.class);

        when(marcaRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(mapper.marcaEntityToMarca(marcaEntity1)).thenReturn(marca1);
        when(mapper.marcaEntityToMarca(marcaEntity2)).thenReturn(marca2);


        List<Marca> resultado = marcaPersistenciaJPA
                .listarMarcasPorNombre(pagina, tamano, ORDEN_DESCENDENTE);


        assertEquals(2, resultado.size());
        assertEquals(marca1, resultado.get(0));
        assertEquals(marca2, resultado.get(1));

        Pageable expectedPageable = PageRequest.of(pagina, tamano, Sort.by(SORT_NOMBRE).descending());
        verify(marcaRepository, times(1)).findAll(expectedPageable);

        verify(mapper, times(1)).marcaEntityToMarca(marcaEntity1);
        verify(mapper, times(1)).marcaEntityToMarca(marcaEntity2);
    }
    
    @Test
    void testObtenerMarcaPorIdCuandoExiste() {
        MarcaEntity marcaEntity = mock(MarcaEntity.class);
        Marca marca = mock(Marca.class);

        when(marcaRepository.findById(1L)).thenReturn(Optional.of(marcaEntity));
        when(mapper.marcaEntityToMarca(marcaEntity)).thenReturn(marca);

        Optional<Marca> resultado = marcaPersistenciaJPA.obtenerMarcaPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(marca, resultado.get());
        verify(marcaRepository, times(1)).findById(1L);
        verify(mapper, times(1)).marcaEntityToMarca(marcaEntity);
    }

    @Test
    void testObtenerMarcaPorIdCuandoNoExiste() {
        when(marcaRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Marca> resultado = marcaPersistenciaJPA.obtenerMarcaPorId(1L);

        assertFalse(resultado.isPresent());
        verify(marcaRepository, times(1)).findById(1L);
        verify(mapper, never()).marcaEntityToMarca(any());
    }
}