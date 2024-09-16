package com.jeffersonvilla.emazon.stock.infraestructura.rest.controller;

import com.jeffersonvilla.emazon.stock.dominio.api.IArticuloServicePort;
import com.jeffersonvilla.emazon.stock.dominio.api.ICategoriaServicePort;
import com.jeffersonvilla.emazon.stock.dominio.api.IMarcaServicePort;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.articulo.ListarArticuloException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.categoria.CategoriaNoExisteException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.marca.MarcaNoExisteException;
import com.jeffersonvilla.emazon.stock.dominio.modelo.Articulo;
import com.jeffersonvilla.emazon.stock.dominio.modelo.Categoria;
import com.jeffersonvilla.emazon.stock.dominio.modelo.Marca;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.articulo.AumentarCantidadStockRequestDto;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.articulo.AumentarCantidadStockResponseDto;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.articulo.CrearArticuloRequestCategoriaDto;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.articulo.CrearArticuloRequestDto;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.articulo.CrearArticuloResponseDto;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.articulo.ListarArticuloReponseCategoriaDto;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.articulo.ListarArticuloResponseDto;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.mapper.ArticuloMapperRest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.LISTAR_POR_ARTICULO;
import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.ORDEN_ASCENDENTE;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorGenerales.ORDENES_VALIDOS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

        articulo = new Articulo.ArticuloBuilder()
                .setId(1L)
                .setNombre("Laptop")
                .setDescripcion("Laptop gaming")
                .setCantidad(5)
                .setPrecio(BigDecimal.valueOf(1200.0))
                .build();

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

    @Test
    void testListarArticulosExito() {
        int pagina = 0;
        int tamano = 10;
        String orden = ORDEN_ASCENDENTE;
        String listarPor = LISTAR_POR_ARTICULO;

        Articulo articulo1 = mock(Articulo.class);
        Articulo articulo2 = mock(Articulo.class);
        List<Articulo> articulos = Arrays.asList(articulo1, articulo2);

        ListarArticuloReponseCategoriaDto categoriaDto = new ListarArticuloReponseCategoriaDto(1L, "C1");

        ListarArticuloResponseDto responseDto1 = new ListarArticuloResponseDto(
                1L, "A1", "D1", 10, BigDecimal.valueOf(1000),
                mock(Marca.class), Set.of(categoriaDto));
        ListarArticuloResponseDto responseDto2 = new ListarArticuloResponseDto(
                2L, "A2", "D2", 20, BigDecimal.valueOf(2000),
                mock(Marca.class), Set.of(categoriaDto));
        List<ListarArticuloResponseDto> responseDtos = Arrays.asList(responseDto1, responseDto2);

        when(articuloApi.listarArticulo(pagina, tamano, orden, listarPor)).thenReturn(articulos);
        when(mapper.articuloToListarArticuloResponseDto(articulo1)).thenReturn(responseDto1);
        when(mapper.articuloToListarArticuloResponseDto(articulo2)).thenReturn(responseDto2);

        ResponseEntity<List<ListarArticuloResponseDto>> resultado =
                articuloController.listarArticulos(pagina, tamano, orden, listarPor);

        assertNotNull(resultado);
        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(responseDtos, resultado.getBody());
        verify(articuloApi, times(1))
                .listarArticulo(pagina, tamano, orden, listarPor);
        verify(mapper, times(1))
                .articuloToListarArticuloResponseDto(articulo1);
        verify(mapper, times(1))
                .articuloToListarArticuloResponseDto(articulo2);
    }

    @Test
    void testListarArticulosSinResultados() {
        int pagina = 0;
        int tamano = 10;
        String orden = ORDEN_ASCENDENTE;
        String listarPor = LISTAR_POR_ARTICULO;

        when(articuloApi.listarArticulo(pagina, tamano, orden, listarPor))
                .thenReturn(Collections.emptyList());

        ResponseEntity<List<ListarArticuloResponseDto>> resultado =
                articuloController.listarArticulos(pagina, tamano, orden, listarPor);

        assertNotNull(resultado);
        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertTrue(resultado.getBody().isEmpty());
        verify(articuloApi, times(1))
                .listarArticulo(pagina, tamano, orden, listarPor);
        verify(mapper, never()).articuloToListarArticuloResponseDto(any(Articulo.class));
    }

    @Test
    void testListarArticulosBadRequest() {
        int pagina = 0;
        int tamano = 10;
        String orden = "invalido";
        String listarPor = "invalido";

        when(articuloApi.listarArticulo(pagina, tamano, orden, listarPor))
                .thenThrow(new ListarArticuloException(ORDENES_VALIDOS));

        assertThrows(ListarArticuloException.class, () -> {
            articuloController.listarArticulos(pagina, tamano, orden, listarPor);
        });

        verify(articuloApi, times(1))
                .listarArticulo(pagina, tamano, orden, listarPor);
        verify(mapper, never()).articuloToListarArticuloResponseDto(any(Articulo.class));
    }

    @Test
    void testAumentarStockExito(){

        AumentarCantidadStockRequestDto request = new AumentarCantidadStockRequestDto(1L, 5);
        AumentarCantidadStockResponseDto response =
                new AumentarCantidadStockResponseDto(1L, "Computador", 15);

        Articulo articuloMock = mock(Articulo.class);

        when(articuloApi.aumentarCantidadStock(request.idArticulo(), request.cantidad())).thenReturn(articuloMock);
        when(mapper.articuloToAumentarCantidadStockResponseDto(articuloMock)).thenReturn(response);

        ResponseEntity<AumentarCantidadStockResponseDto> respuesta = articuloController.aumentarStock(request);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(response, respuesta.getBody());

        verify(articuloApi).aumentarCantidadStock(request.idArticulo(), request.cantidad());
        verify(mapper).articuloToAumentarCantidadStockResponseDto(articuloMock);
    }

}