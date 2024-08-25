package com.jeffersonvilla.emazon.stock.infraestructura.rest.controller;

import com.jeffersonvilla.emazon.stock.dominio.api.ICategoriaServicePort;
import com.jeffersonvilla.emazon.stock.dominio.modelo.Categoria;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.CrearCategoriaRequestDto;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.CrearCategoriaResponseDto;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.ListarCategoriaResponseDto;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.mapper.CategoriaMapperRest;

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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoriaController.class)
class CategoriaControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICategoriaServicePort categoriaApi;

    @MockBean
    private CategoriaMapperRest mapper;

    @Test
    void testListarCategorias() throws Exception {
        ListarCategoriaResponseDto categoriaDto1 = new ListarCategoriaResponseDto(
                1L, "Electrónica", "Descripcion");
        ListarCategoriaResponseDto categoriaDto2 = new ListarCategoriaResponseDto(
                2L, "Ropa", "Descripcion");

        List<Categoria> categorias = Arrays.asList(mock(Categoria.class), mock(Categoria.class));

        when(categoriaApi.listarCategoria(0, 10, "ASC"))
                .thenReturn(categorias);
        when(mapper.categoriaToListarCategoriaResponseDto(categorias.get(0)))
                .thenReturn(categoriaDto1);
        when(mapper.categoriaToListarCategoriaResponseDto(categorias.get(1)))
                .thenReturn(categoriaDto2);

        mockMvc.perform(get("/categoria/listar")
                        .param("pagina", "0")
                        .param("tamano", "10")
                        .param("orden", "ASC")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(categoriaDto1.id()))
                .andExpect(jsonPath("$[0].nombre").value(categoriaDto1.nombre()))
                .andExpect(jsonPath("$[1].id").value(categoriaDto2.id()))
                .andExpect(jsonPath("$[1].nombre").value(categoriaDto2.nombre()));
    }

    @Test
    void testCrearCategoria() throws Exception {
        long id = 1L;
        String nombre = "Electrónica";
        String descripcion = "Categoría de dispositivos electrónicos";

        Categoria categoria = mock(Categoria.class);
        CrearCategoriaResponseDto crearCategoriaResponseDto = new CrearCategoriaResponseDto(
                id, nombre, descripcion);

        when(mapper.crearCategoriaRequestDtoToCategoria(any(CrearCategoriaRequestDto.class)))
                .thenReturn(categoria);
        when(categoriaApi.crearCategoria(any(Categoria.class))).thenReturn(categoria);
        when(mapper.categoriaToCrearCategoriaResponseDto(any(Categoria.class)))
                .thenReturn(crearCategoriaResponseDto);

        String requestJson = "{\"nombre\": \""+nombre+"\", \"descripcion\": \""+descripcion+"\"}";

        mockMvc.perform(post("/categoria/crear")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.nombre").value(nombre))
                .andExpect(jsonPath("$.descripcion").value(descripcion));
    }
}
