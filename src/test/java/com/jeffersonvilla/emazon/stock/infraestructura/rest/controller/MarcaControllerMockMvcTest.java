package com.jeffersonvilla.emazon.stock.infraestructura.rest.controller;

import com.jeffersonvilla.emazon.stock.dominio.api.IMarcaServicePort;
import com.jeffersonvilla.emazon.stock.dominio.modelo.Marca;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.marca.ListarMarcaResponseDto;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.marca.CrearMarcaRequestDto;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.marca.CrearMarcaResponseDto;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.mapper.MarcaMapperRest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @Test
    void testListarMarcas() throws Exception {
        ListarMarcaResponseDto marcaDto1 = new ListarMarcaResponseDto(
                1L, "Marca A", "Descripcion");
        ListarMarcaResponseDto marcaDto2 = new ListarMarcaResponseDto(
                2L, "Marca B", "Descripcion");

        List<Marca> marcas = Arrays.asList(mock(Marca.class), mock(Marca.class));

        when(marcaApi.listarMarca(0, 10, "ASC"))
                .thenReturn(marcas);
        when(mapper.marcaToListarMarcaResponseDto(marcas.get(0)))
                .thenReturn(marcaDto1);
        when(mapper.marcaToListarMarcaResponseDto(marcas.get(1)))
                .thenReturn(marcaDto2);

        mockMvc.perform(get("/marca/listar")
                        .param("pagina", "0")
                        .param("tamano", "10")
                        .param("orden", "ASC")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(marcaDto1.id()))
                .andExpect(jsonPath("$[0].nombre").value(marcaDto1.nombre()))
                .andExpect(jsonPath("$[1].id").value(marcaDto2.id()))
                .andExpect(jsonPath("$[1].nombre").value(marcaDto2.nombre()));
    }
}