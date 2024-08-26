package com.jeffersonvilla.emazon.stock.infraestructura.rest.controller;

import com.jeffersonvilla.emazon.stock.dominio.api.IMarcaServicePort;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.marca.CreacionMarcaException;
import com.jeffersonvilla.emazon.stock.dominio.modelo.Marca;
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

import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorGenerales.NOMBRE_NO_DISPONIBLE;
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
}