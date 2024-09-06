package com.jeffersonvilla.emazon.stock.infraestructura.rest.controller;

import com.jeffersonvilla.emazon.stock.dominio.api.IArticuloServicePort;
import com.jeffersonvilla.emazon.stock.dominio.api.ICategoriaServicePort;
import com.jeffersonvilla.emazon.stock.dominio.api.IMarcaServicePort;
import com.jeffersonvilla.emazon.stock.dominio.modelo.Articulo;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.articulo.CrearArticuloRequestDto;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.articulo.CrearArticuloResponseDto;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.articulo.ListarArticuloResponseDto;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.mapper.ArticuloMapperRest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorGenerales.ATRIBUTOS_PARA_LISTAR;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorGenerales.ORDENES_VALIDOS;
import static com.jeffersonvilla.emazon.stock.infraestructura.rest.util.Constantes.LISTAR_POR_DEFAULT;
import static com.jeffersonvilla.emazon.stock.infraestructura.rest.util.Constantes.ORDEN_DEFAULT;
import static com.jeffersonvilla.emazon.stock.infraestructura.rest.util.Constantes.PAGINA_DEFAULT;
import static com.jeffersonvilla.emazon.stock.infraestructura.rest.util.Constantes.TAMANO_DEFAULT;

@Tag(name = "Articulo API", description = "Operaciones relacionadas con los artículos")
@RequiredArgsConstructor
@RestController
@RequestMapping("articulo")
public class ArticuloController {

    private final IArticuloServicePort articuloApi;
    private final IMarcaServicePort marcaApi;
    private final ICategoriaServicePort categoriaApi;
    private final ArticuloMapperRest mapper;

    @Operation(summary = "Listar artículos",
            description = "Obtiene una lista de artículos con paginación y ordenamiento. \n"+
                    "Parámetros:\n" +
                    "- **listarPor**: " + ATRIBUTOS_PARA_LISTAR + "\n" +
                    "- **orden**: " + ORDENES_VALIDOS
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de artículos obtenida exitosamente",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ListarArticuloResponseDto.class)))),
            @ApiResponse(responseCode = "400", description = "Parámetros de solicitud inválidos",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("listar")
    public ResponseEntity<List<ListarArticuloResponseDto>> listarArticulos(
            @RequestParam(defaultValue = PAGINA_DEFAULT) int pagina,
            @RequestParam(defaultValue = TAMANO_DEFAULT) int tamano,
            @RequestParam(defaultValue = ORDEN_DEFAULT) String orden,
            @RequestParam(defaultValue = LISTAR_POR_DEFAULT) String listarPor
    ){

        return new ResponseEntity<>(
                articuloApi.listarArticulo(pagina, tamano, orden, listarPor)
                        .stream()
                        .map(mapper::articuloToListarArticuloResponseDto)
                        .toList(),
                HttpStatus.OK
        );
    }

    @Operation(summary = "Crear nuevo artículo",
            description = "Crea un nuevo artículo con los datos suministrados en el body.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Artículo creado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CrearArticuloResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("crear")
    public ResponseEntity<CrearArticuloResponseDto> crearArticulo(
            @Valid
            @RequestBody
            CrearArticuloRequestDto articuloDto
    ){

        Articulo articulo = mapper.crearArticuloRequestDtoToArticulo(articuloDto);
        articulo.setMarca(marcaApi.obtenerMarcaPorId(articuloDto.marcaId()));
        articuloDto.categorias().stream()
                .map(dto -> categoriaApi.obtenerCategoriaPorId(dto.categoriaId()))
                .forEach(articulo::agregarCategoria);

        return new ResponseEntity<>(
                mapper.articuloToCrearArticuloResponseDto(
                        articuloApi.crearArticulo(articulo)
                ),
                HttpStatus.CREATED
        );
    }
}
