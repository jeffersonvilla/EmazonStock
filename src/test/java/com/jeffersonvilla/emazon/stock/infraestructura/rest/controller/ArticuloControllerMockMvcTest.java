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
import com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.articulo.ListarArticuloReponseCategoriaDto;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.articulo.ListarArticuloResponseDto;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.mapper.ArticuloMapperRest;
import com.jeffersonvilla.emazon.stock.infraestructura.seguridad.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.LISTAR_POR_ARTICULO;
import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.ORDEN_ASCENDENTE;
import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.ROL_ADMIN;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ArticuloController.class)
@WithMockUser(username = "admin", authorities = {ROL_ADMIN})
class ArticuloControllerMockMvcTest {

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private ArticuloMapperRest mapper;

    @MockBean
    private IMarcaServicePort marcaApi;

    @MockBean
    private ICategoriaServicePort categoriaApi;

    @MockBean
    private IArticuloServicePort articuloApi;

    @MockBean
    private JwtService jwtService;

    private CrearArticuloRequestDto crearArticuloRequestDto;
    private Articulo articulo;
    private Marca marca;
    private Categoria categoria;
    private CrearArticuloResponseDto crearArticuloResponseDto;

    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

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
                        .with(csrf())
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
                        .with(csrf())
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
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(crearArticuloRequestDto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Categoría no existe"));
    }

    @Test
    void testListarArticulos() throws Exception {
        Marca marca1 = mock(Marca.class);
        Marca marca2 = mock(Marca.class);
        ListarArticuloReponseCategoriaDto categoria1 = mock(ListarArticuloReponseCategoriaDto.class);
        ListarArticuloReponseCategoriaDto categoria2 = mock(ListarArticuloReponseCategoriaDto.class);

        ListarArticuloResponseDto articuloDto1 = new ListarArticuloResponseDto(
                1L, "A1", "D1", 10, BigDecimal.valueOf(1000),
                marca1, Set.of(categoria1));
        ListarArticuloResponseDto articuloDto2 = new ListarArticuloResponseDto(
                2L, "A2", "D2", 20, BigDecimal.valueOf(2000),
                marca2, Set.of(categoria2));

        List<Articulo> articulos = Arrays.asList(mock(Articulo.class), mock(Articulo.class));

        when(articuloApi.listarArticulo(0, 10, ORDEN_ASCENDENTE, LISTAR_POR_ARTICULO))
                .thenReturn(articulos);
        when(mapper.articuloToListarArticuloResponseDto(articulos.get(0)))
                .thenReturn(articuloDto1);
        when(mapper.articuloToListarArticuloResponseDto(articulos.get(1)))
                .thenReturn(articuloDto2);

        mockMvc.perform(get("/articulo/listar")
                        .param("pagina", "0")
                        .param("tamano", "10")
                        .param("orden", ORDEN_ASCENDENTE)
                        .param("listarPor", LISTAR_POR_ARTICULO)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(articuloDto1.id()))
                .andExpect(jsonPath("$[0].nombre").value(articuloDto1.nombre()))
                .andExpect(jsonPath("$[0].descripcion").value(articuloDto1.descripcion()))
                .andExpect(jsonPath("$[0].cantidad").value(articuloDto1.cantidad()))
                .andExpect(jsonPath("$[0].precio").value(articuloDto1.precio()))
                .andExpect(jsonPath("$[0].marca.id").value(articuloDto1.marca().getId()))
                .andExpect(jsonPath("$[0].marca.nombre").value(articuloDto1.marca().getNombre()))
                .andExpect(jsonPath("$[0].marca.descripcion").value(articuloDto1.marca().getDescripcion()))
                .andExpect(jsonPath("$[0].categorias[0].id").value(categoria1.id()))
                .andExpect(jsonPath("$[0].categorias[0].nombre").value(categoria1.nombre()))
                .andExpect(jsonPath("$[1].id").value(articuloDto2.id()))
                .andExpect(jsonPath("$[1].nombre").value(articuloDto2.nombre()))
                .andExpect(jsonPath("$[1].descripcion").value(articuloDto2.descripcion()))
                .andExpect(jsonPath("$[1].cantidad").value(articuloDto2.cantidad()))
                .andExpect(jsonPath("$[1].precio").value(articuloDto2.precio()))
                .andExpect(jsonPath("$[1].marca.id").value(articuloDto2.marca().getId()))
                .andExpect(jsonPath("$[1].marca.nombre").value(articuloDto2.marca().getNombre()))
                .andExpect(jsonPath("$[1].marca.descripcion").value(articuloDto2.marca().getDescripcion()))
                .andExpect(jsonPath("$[1].categorias[0].id").value(categoria2.id()))
                .andExpect(jsonPath("$[1].categorias[0].nombre").value(categoria2.nombre()));

    }
}