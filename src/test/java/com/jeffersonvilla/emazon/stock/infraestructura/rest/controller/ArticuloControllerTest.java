package com.jeffersonvilla.emazon.stock.infraestructura.rest.controller;

import com.jeffersonvilla.emazon.stock.dominio.api.IArticuloServicePort;
import com.jeffersonvilla.emazon.stock.dominio.api.ICategoriaServicePort;
import com.jeffersonvilla.emazon.stock.dominio.api.IMarcaServicePort;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.categoria.CategoriaNoExisteException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.marca.MarcaNoExisteException;
import com.jeffersonvilla.emazon.stock.dominio.modelo.Articulo;
import com.jeffersonvilla.emazon.stock.dominio.modelo.Categoria;
import com.jeffersonvilla.emazon.stock.dominio.modelo.Marca;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.articulo.CrearArticuloRequestCategoriaDto;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.articulo.CrearArticuloRequestDto;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.articulo.CrearArticuloResponseDto;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.mapper.ArticuloMapperRest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ArticuloControllerTest {

    @Mock
    private IArticuloServicePort articuloApi;

    @Mock
    private IMarcaServicePort marcaApi;

    @Mock
    private ICategoriaServicePort categoriaApi;

    @Mock
    private ArticuloMapperRest mapper;

    @InjectMocks
    private ArticuloController articuloController;

    private CrearArticuloRequestDto crearArticuloRequestDto;
    private Articulo articulo;
    private Marca marca;
    private Categoria categoria;
    private CrearArticuloResponseDto crearArticuloResponseDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        marca = mock(Marca.class);
        categoria = mock(Categoria.class);

        crearArticuloRequestDto = new CrearArticuloRequestDto(
                "Laptop",
                "Laptop gaming",
                10,
                BigDecimal.valueOf(1200.00),
                1L,
                Set.of(new CrearArticuloRequestCategoriaDto(1L))
        );

        articulo = new Articulo(1L, "Laptop", "Laptop gaming",
                10, BigDecimal.valueOf(1200.00), null, null);

        crearArticuloResponseDto = new CrearArticuloResponseDto(1L, "Laptop",
                "Laptop gaming", 10, BigDecimal.valueOf(1200.00),
                marca, Set.of(categoria));
    }

    @Test
    void testCrearArticuloConExito() {
        when(mapper.crearArticuloRequestDtoToArticulo(crearArticuloRequestDto)).thenReturn(articulo);
        when(marcaApi.obtenerMarcaPorId(anyLong())).thenReturn(marca);
        when(categoriaApi.obtenerCategoriaPorId(anyLong())).thenReturn(categoria);
        when(articuloApi.crearArticulo(articulo)).thenReturn(articulo);
        when(mapper.articuloToCrearArticuloResponseDto(articulo)).thenReturn(crearArticuloResponseDto);

        ResponseEntity<CrearArticuloResponseDto> response =
                articuloController.crearArticulo(crearArticuloRequestDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(crearArticuloResponseDto, response.getBody());

        verify(mapper, times(1)).
                crearArticuloRequestDtoToArticulo(crearArticuloRequestDto);
        verify(marcaApi, times(1)).obtenerMarcaPorId(anyLong());
        verify(categoriaApi, times(1)).obtenerCategoriaPorId(anyLong());
        verify(articuloApi, times(1)).crearArticulo(articulo);
        verify(mapper, times(1)).articuloToCrearArticuloResponseDto(articulo);
    }

    @Test
    void testCrearArticuloCuandoMarcaNoExiste() {
        when(mapper.crearArticuloRequestDtoToArticulo(crearArticuloRequestDto)).thenReturn(articulo);
        when(marcaApi.obtenerMarcaPorId(anyLong())).thenThrow(new MarcaNoExisteException("Marca no existe"));

        Exception exception = assertThrows(MarcaNoExisteException.class, () -> {
            articuloController.crearArticulo(crearArticuloRequestDto);
        });

        assertEquals("Marca no existe", exception.getMessage());
        verify(mapper, times(1)).
                crearArticuloRequestDtoToArticulo(crearArticuloRequestDto);
        verify(marcaApi, times(1)).obtenerMarcaPorId(anyLong());
        verify(categoriaApi, never()).obtenerCategoriaPorId(anyLong());
        verify(articuloApi, never()).crearArticulo(any());
        verify(mapper, never()).articuloToCrearArticuloResponseDto(any());
    }

    @Test
    void testCrearArticuloCuandoCategoriaNoExiste() {
        when(mapper.crearArticuloRequestDtoToArticulo(crearArticuloRequestDto)).thenReturn(articulo);
        when(marcaApi.obtenerMarcaPorId(anyLong())).thenReturn(marca);
        when(categoriaApi.obtenerCategoriaPorId(anyLong())).
                thenThrow(new CategoriaNoExisteException("Categoría no existe"));

        Exception exception = assertThrows(CategoriaNoExisteException.class, () -> {
            articuloController.crearArticulo(crearArticuloRequestDto);
        });

        assertEquals("Categoría no existe", exception.getMessage());
        verify(mapper, times(1)).
                crearArticuloRequestDtoToArticulo(crearArticuloRequestDto);
        verify(marcaApi, times(1)).obtenerMarcaPorId(anyLong());
        verify(categoriaApi, times(1)).obtenerCategoriaPorId(anyLong());
        verify(articuloApi, never()).crearArticulo(any());
        verify(mapper, never()).articuloToCrearArticuloResponseDto(any());
    }
}