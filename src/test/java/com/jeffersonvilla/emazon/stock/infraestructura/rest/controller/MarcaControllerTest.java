package com.jeffersonvilla.emazon.stock.infraestructura.rest.controller;

import com.jeffersonvilla.emazon.stock.dominio.api.IMarcaServicePort;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.marca.ListarMarcaException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.marca.CreacionMarcaException;
import com.jeffersonvilla.emazon.stock.dominio.modelo.Marca;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.marca.ListarMarcaResponseDto;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.marca.CrearMarcaRequestDto;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.marca.CrearMarcaResponseDto;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.mapper.MarcaMapperRest;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

class MarcaControllerTest {

    @Mock
    private IMarcaServicePort marcaApi;

    @Mock
    private MarcaMapperRest mapper;

    @InjectMocks
    private MarcaController marcaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearMarcaExito(){
        String nombre = "Marca A";
        String descripcion = "Marca de dispositivos electrónicos";

        CrearMarcaRequestDto requestDto = new CrearMarcaRequestDto(nombre, descripcion);
        Marca marca = new Marca(1L, nombre, descripcion);
        CrearMarcaResponseDto responseDto = new CrearMarcaResponseDto(1L, nombre, descripcion);

        when(mapper.crearMarcaRequestDtoToMarca(any(CrearMarcaRequestDto.class)))
                .thenReturn(marca);
        when(marcaApi.crearMarca(any(Marca.class))).thenReturn(marca);
        when(mapper.marcaToCrearMarcaResponseDto(any(Marca.class))).thenReturn(responseDto);


        ResponseEntity<CrearMarcaResponseDto> resultado = marcaController
                .crearMarca(requestDto);

        assertNotNull(resultado);
        assertEquals(HttpStatus.CREATED, resultado.getStatusCode());
        assertEquals(responseDto, resultado.getBody());
        verify(mapper, times(1))
                .crearMarcaRequestDtoToMarca(any(CrearMarcaRequestDto.class));
        verify(marcaApi, times(1)).crearMarca(any(Marca.class));
        verify(mapper, times(1))
                .marcaToCrearMarcaResponseDto(any(Marca.class));

    }

    @Test
    void testCrearMarcaBadRequest() {
        String nombre = "Marca en uso";
        String descripcion = "Marca en uso";

        CrearMarcaRequestDto requestDto = new CrearMarcaRequestDto(nombre, descripcion);
        Marca marca = new Marca(1L, nombre, descripcion);

        when(mapper.crearMarcaRequestDtoToMarca(any(CrearMarcaRequestDto.class)))
                .thenReturn(marca);
        when(marcaApi.crearMarca(any(Marca.class)))
                .thenThrow(new CreacionMarcaException(NOMBRE_NO_DISPONIBLE));

        assertThrows(Exception.class, () -> {
            marcaController.crearMarca(requestDto);
        });

        verify(mapper, times(1)).crearMarcaRequestDtoToMarca(any(CrearMarcaRequestDto.class));
        verify(marcaApi, times(1)).crearMarca(any(Marca.class));
        verify(mapper, never()).marcaToCrearMarcaResponseDto(any(Marca.class));
    }

    @Test
    void testListarMarcasExito() {
        int pagina = 0;
        int tamano = 10;
        String orden = "ascendente";

        Marca marca1 = new Marca(1L, "Marca A", "Descripción");
        Marca marca2 = new Marca(2L, "Marca B", "Descripción");
        List<Marca> marcas = Arrays.asList(marca1, marca2);

        ListarMarcaResponseDto responseDto1 =
                new ListarMarcaResponseDto(1L, "Marca A", "Descripción");
        ListarMarcaResponseDto responseDto2 =
                new ListarMarcaResponseDto(2L, "Marca B", "Descripción");
        List<ListarMarcaResponseDto> responseDtos = Arrays.asList(responseDto1, responseDto2);

        when(marcaApi.listarMarca(pagina, tamano, orden)).thenReturn(marcas);
        when(mapper.marcaToListarMarcaResponseDto(marca1)).thenReturn(responseDto1);
        when(mapper.marcaToListarMarcaResponseDto(marca2)).thenReturn(responseDto2);

        ResponseEntity<List<ListarMarcaResponseDto>> resultado =
                marcaController.listarMarcas(pagina, tamano, orden);

        assertNotNull(resultado);
        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(responseDtos, resultado.getBody());
        verify(marcaApi, times(1)).listarMarca(pagina, tamano, orden);
        verify(mapper, times(1)).marcaToListarMarcaResponseDto(marca1);
        verify(mapper, times(1)).marcaToListarMarcaResponseDto(marca2);
    }

    @Test
    void testListarMarcasSinResultados() {
        int pagina = 0;
        int tamano = 10;
        String orden = "ascendente";

        when(marcaApi.listarMarca(pagina, tamano, orden)).thenReturn(Collections.emptyList());

        ResponseEntity<List<ListarMarcaResponseDto>> resultado =
                marcaController.listarMarcas(pagina, tamano, orden);

        assertNotNull(resultado);
        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertTrue(resultado.getBody().isEmpty());
        verify(marcaApi, times(1)).listarMarca(pagina, tamano, orden);
        verify(mapper, never()).marcaToListarMarcaResponseDto(any(Marca.class));
    }

    @Test
    void testListarMarcasBadRequest() {
        int pagina = 0;
        int tamano = 10;
        String orden = "invalid";

        when(marcaApi.listarMarca(pagina, tamano, orden))
                .thenThrow(new ListarMarcaException(ORDENES_VALIDOS));

        assertThrows(ListarMarcaException.class, () -> {
            marcaController.listarMarcas(pagina, tamano, orden);
        });

        verify(marcaApi, times(1)).listarMarca(pagina, tamano, orden);
        verify(mapper, never()).marcaToListarMarcaResponseDto(any(Marca.class));
    }

}