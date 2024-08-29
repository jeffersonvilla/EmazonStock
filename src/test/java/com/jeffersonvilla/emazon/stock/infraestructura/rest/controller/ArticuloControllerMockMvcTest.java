package com.jeffersonvilla.emazon.stock.infraestructura.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ArticuloController.class)
class ArticuloControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ArticuloMapperRest mapper;

    @MockBean
    private IMarcaServicePort marcaApi;

    @MockBean
    private ICategoriaServicePort categoriaApi;

    @MockBean
    private IArticuloServicePort articuloApi;

    private CrearArticuloRequestDto crearArticuloRequestDto;
    private Articulo articulo;
    private Marca marca;
    private Categoria categoria;
    private CrearArticuloResponseDto crearArticuloResponseDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        marca = new Marca(1L, "Marca1", "Descripción marca");
        categoria = new Categoria(1L, "Electrónica",
                "Dispositivos electrónicos");

        crearArticuloRequestDto = new CrearArticuloRequestDto(
                "Laptop",
                "Laptop gaming",
                10,
                BigDecimal.valueOf(1200.00),
                1L, // marcaId
                Set.of(new CrearArticuloRequestCategoriaDto(1L))
        );

        articulo = new Articulo(1L, "Laptop", "Laptop gaming",
                10, BigDecimal.valueOf(1200.00), null, null);

        crearArticuloResponseDto = new CrearArticuloResponseDto(1L, "Laptop",
                "Laptop gaming", 10, BigDecimal.valueOf(1200.00),
                marca, Set.of(categoria));
    }

    @Test
    void testCrearArticuloConExito() throws Exception {
        when(mapper.crearArticuloRequestDtoToArticulo(crearArticuloRequestDto)).thenReturn(articulo);
        when(marcaApi.obtenerMarcaPorId(1L)).thenReturn(marca);
        when(categoriaApi.obtenerCategoriaPorId(1L)).thenReturn(categoria);
        when(articuloApi.crearArticulo(articulo)).thenReturn(articulo);
        when(mapper.articuloToCrearArticuloResponseDto(articulo)).thenReturn(crearArticuloResponseDto);

        String requestJson = objectMapper.writeValueAsString(crearArticuloRequestDto);

        mockMvc.perform(post("/articulo/crear")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Laptop"))
                .andExpect(jsonPath("$.descripcion").value("Laptop gaming"))
                .andExpect(jsonPath("$.cantidad").value(10))
                .andExpect(jsonPath("$.precio").value(1200.00))
                .andExpect(jsonPath("$.marca.id").value(1))
                .andExpect(jsonPath("$.marca.nombre").value("Marca1"))
                .andExpect(jsonPath("$.categorias[0].id").value(1))
                .andExpect(jsonPath("$.categorias[0].nombre").value("Electrónica"))
                .andExpect(jsonPath("$.categorias[0].descripcion").value("Dispositivos electrónicos"));
    }

    @Test
    void testCrearArticuloCuandoMarcaNoExiste() throws Exception {
        when(mapper.crearArticuloRequestDtoToArticulo(crearArticuloRequestDto)).thenReturn(articulo);
        when(marcaApi.obtenerMarcaPorId(1L)).thenThrow(new MarcaNoExisteException("Marca no existe"));

        mockMvc.perform(post("/articulo/crear")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(crearArticuloRequestDto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Marca no existe"));
    }

    @Test
    void testCrearArticuloCuandoCategoriaNoExiste() throws Exception {
        when(mapper.crearArticuloRequestDtoToArticulo(crearArticuloRequestDto)).thenReturn(articulo);
        when(marcaApi.obtenerMarcaPorId(1L)).thenReturn(marca);
        when(categoriaApi.obtenerCategoriaPorId(1L)).thenThrow(new CategoriaNoExisteException("Categoría no existe"));

        mockMvc.perform(post("/articulo/crear")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(crearArticuloRequestDto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Categoría no existe"));
    }
}