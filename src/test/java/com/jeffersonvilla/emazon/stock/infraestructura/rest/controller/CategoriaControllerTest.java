package com.jeffersonvilla.emazon.stock.infraestructura.rest.controller;

import com.jeffersonvilla.emazon.stock.dominio.api.ICategoriaServicePort;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.CreacionCategoriaException;
import com.jeffersonvilla.emazon.stock.dominio.modelo.Categoria;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.CrearCategoriaRequestDto;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.CrearCategoriaResponseDto;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.mapper.CategoriaMapperRest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorGenerales.NOMBRE_NO_DISPONIBLE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class CategoriaControllerTest {

    @Mock
    private ICategoriaServicePort crearCategoriaApi;

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
        when(crearCategoriaApi.crearCategoria(any(Categoria.class))).thenReturn(categoria);
        when(mapper.categoriaToCrearCategoriaResponseDto(any(Categoria.class))).thenReturn(responseDto);


        ResponseEntity<CrearCategoriaResponseDto> resultado = categoriaController
                .crearCategoria(requestDto);

        assertNotNull(resultado);
        assertEquals(HttpStatus.CREATED, resultado.getStatusCode());
        assertEquals(responseDto, resultado.getBody());
        verify(mapper, times(1))
                .crearCategoriaRequestDtoToCategoria(any(CrearCategoriaRequestDto.class));
        verify(crearCategoriaApi, times(1)).crearCategoria(any(Categoria.class));
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
        when(crearCategoriaApi.crearCategoria(any(Categoria.class)))
                .thenThrow(new CreacionCategoriaException(NOMBRE_NO_DISPONIBLE));

        assertThrows(Exception.class, () -> {
            categoriaController.crearCategoria(requestDto);
        });

        verify(mapper, times(1)).crearCategoriaRequestDtoToCategoria(any(CrearCategoriaRequestDto.class));
        verify(crearCategoriaApi, times(1)).crearCategoria(any(Categoria.class));
        verify(mapper, never()).categoriaToCrearCategoriaResponseDto(any(Categoria.class));
    }
}
