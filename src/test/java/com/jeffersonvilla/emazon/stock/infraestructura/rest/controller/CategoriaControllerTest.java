package com.jeffersonvilla.emazon.stock.infraestructura.rest.controller;

import com.jeffersonvilla.emazon.stock.dominio.api.ICategoriaServicePort;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.categoria.CreacionCategoriaException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.categoria.ListarCategoriaException;
import com.jeffersonvilla.emazon.stock.dominio.modelo.Categoria;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.categoria.CrearCategoriaRequestDto;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.categoria.CrearCategoriaResponseDto;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.categoria.ListarCategoriaResponseDto;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.mapper.CategoriaMapperRest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorGenerales.NOMBRE_NO_DISPONIBLE;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorGenerales.ORDENES_VALIDOS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CategoriaControllerTest {

    @Mock
    private ICategoriaServicePort categoriaApi;

    @Mock
    private CategoriaMapperRest mapper;

    @InjectMocks
    private CategoriaController categoriaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearCategoriaExito(){
        String nombre = "Electrónica";
        String descripcion = "Categoría de dispositivos electrónicos";

        CrearCategoriaRequestDto requestDto = new CrearCategoriaRequestDto(nombre, descripcion);
        Categoria categoria = new Categoria(1L, nombre, descripcion);
        CrearCategoriaResponseDto responseDto = new CrearCategoriaResponseDto(1L, nombre, descripcion);

        when(mapper.crearCategoriaRequestDtoToCategoria(any(CrearCategoriaRequestDto.class)))
                .thenReturn(categoria);
        when(categoriaApi.crearCategoria(any(Categoria.class))).thenReturn(categoria);
        when(mapper.categoriaToCrearCategoriaResponseDto(any(Categoria.class))).thenReturn(responseDto);


        ResponseEntity<CrearCategoriaResponseDto> resultado = categoriaController
                .crearCategoria(requestDto);

        assertNotNull(resultado);
        assertEquals(HttpStatus.CREATED, resultado.getStatusCode());
        assertEquals(responseDto, resultado.getBody());
        verify(mapper, times(1))
                .crearCategoriaRequestDtoToCategoria(any(CrearCategoriaRequestDto.class));
        verify(categoriaApi, times(1)).crearCategoria(any(Categoria.class));
        verify(mapper, times(1))
                .categoriaToCrearCategoriaResponseDto(any(Categoria.class));

    }

    @Test
    void testCrearCategoriaBadRequest() {
        String nombre = "Categoría en uso";
        String descripcion = "Categoría en uso";

        CrearCategoriaRequestDto requestDto = new CrearCategoriaRequestDto(nombre, descripcion);
        Categoria categoria = new Categoria(1L, nombre, descripcion);

        when(mapper.crearCategoriaRequestDtoToCategoria(any(CrearCategoriaRequestDto.class)))
                .thenReturn(categoria);
        when(categoriaApi.crearCategoria(any(Categoria.class)))
                .thenThrow(new CreacionCategoriaException(NOMBRE_NO_DISPONIBLE));

        assertThrows(Exception.class, () -> {
            categoriaController.crearCategoria(requestDto);
        });

        verify(mapper, times(1)).crearCategoriaRequestDtoToCategoria(any(CrearCategoriaRequestDto.class));
        verify(categoriaApi, times(1)).crearCategoria(any(Categoria.class));
        verify(mapper, never()).categoriaToCrearCategoriaResponseDto(any(Categoria.class));
    }

    @Test
    void testListarCategoriasExito() {
        int pagina = 0;
        int tamano = 10;
        String orden = "ascendente";

        Categoria categoria1 = new Categoria(1L, "Electrónica", "Descripción");
        Categoria categoria2 = new Categoria(2L, "Hogar", "Descripción");
        List<Categoria> categorias = Arrays.asList(categoria1, categoria2);

        ListarCategoriaResponseDto responseDto1 =
                new ListarCategoriaResponseDto(1L, "Electrónica", "Descripción");
        ListarCategoriaResponseDto responseDto2 =
                new ListarCategoriaResponseDto(2L, "Hogar", "Descripción");
        List<ListarCategoriaResponseDto> responseDtos = Arrays.asList(responseDto1, responseDto2);

        when(categoriaApi.listarCategoria(pagina, tamano, orden)).thenReturn(categorias);
        when(mapper.categoriaToListarCategoriaResponseDto(categoria1)).thenReturn(responseDto1);
        when(mapper.categoriaToListarCategoriaResponseDto(categoria2)).thenReturn(responseDto2);

        ResponseEntity<List<ListarCategoriaResponseDto>> resultado =
                categoriaController.listarCategorias(pagina, tamano, orden);

        assertNotNull(resultado);
        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(responseDtos, resultado.getBody());
        verify(categoriaApi, times(1)).listarCategoria(pagina, tamano, orden);
        verify(mapper, times(1)).categoriaToListarCategoriaResponseDto(categoria1);
        verify(mapper, times(1)).categoriaToListarCategoriaResponseDto(categoria2);
    }

    @Test
    void testListarCategoriasSinResultados() {
        int pagina = 0;
        int tamano = 10;
        String orden = "ascendente";

        when(categoriaApi.listarCategoria(pagina, tamano, orden)).thenReturn(Collections.emptyList());

        ResponseEntity<List<ListarCategoriaResponseDto>> resultado =
                categoriaController.listarCategorias(pagina, tamano, orden);

        assertNotNull(resultado);
        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertTrue(resultado.getBody().isEmpty());
        verify(categoriaApi, times(1)).listarCategoria(pagina, tamano, orden);
        verify(mapper, never()).categoriaToListarCategoriaResponseDto(any(Categoria.class));
    }

    @Test
    void testListarCategoriasBadRequest() {
        int pagina = 0;
        int tamano = 10;
        String orden = "invalid";

        when(categoriaApi.listarCategoria(pagina, tamano, orden))
                .thenThrow(new ListarCategoriaException(ORDENES_VALIDOS));

        assertThrows(ListarCategoriaException.class, () -> {
            categoriaController.listarCategorias(pagina, tamano, orden);
        });

        verify(categoriaApi, times(1)).listarCategoria(pagina, tamano, orden);
        verify(mapper, never()).categoriaToListarCategoriaResponseDto(any(Categoria.class));
    }

}
