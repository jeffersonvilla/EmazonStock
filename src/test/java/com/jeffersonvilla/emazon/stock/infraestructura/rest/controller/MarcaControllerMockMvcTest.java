package com.jeffersonvilla.emazon.stock.infraestructura.rest.controller;

import com.jeffersonvilla.emazon.stock.dominio.api.IMarcaServicePort;
import com.jeffersonvilla.emazon.stock.dominio.modelo.Marca;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.marca.CrearMarcaRequestDto;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.marca.CrearMarcaResponseDto;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.mapper.MarcaMapperRest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(MarcaController.class)
class MarcaControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IMarcaServicePort marcaApi;

    @MockBean
    private MarcaMapperRest mapper;

    @Test
    void testCrearMarca() throws Exception {
        long id = 1L;
        String nombre = "Marca A";
        String descripcion = "Marca de dispositivos electrónicos";

        Marca marca = mock(Marca.class);
        CrearMarcaResponseDto crearMarcaResponseDto = new CrearMarcaResponseDto(
                id, nombre, descripcion);

        when(mapper.crearMarcaRequestDtoToMarca(any(CrearMarcaRequestDto.class)))
                .thenReturn(marca);
        when(marcaApi.crearMarca(any(Marca.class))).thenReturn(marca);
        when(mapper.marcaToCrearMarcaResponseDto(any(Marca.class)))
                .thenReturn(crearMarcaResponseDto);

        String requestJson = "{\"nombre\": \""+nombre+"\", \"descripcion\": \""+descripcion+"\"}";

        mockMvc.perform(post("/marca/crear")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.nombre").value(nombre))
                .andExpect(jsonPath("$.descripcion").value(descripcion));
    }
}