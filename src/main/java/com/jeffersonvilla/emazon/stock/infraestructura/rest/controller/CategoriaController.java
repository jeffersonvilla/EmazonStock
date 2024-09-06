package com.jeffersonvilla.emazon.stock.infraestructura.rest.controller;

import com.jeffersonvilla.emazon.stock.dominio.api.ICategoriaServicePort;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.categoria.CrearCategoriaRequestDto;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.categoria.CrearCategoriaResponseDto;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.categoria.ListarCategoriaResponseDto;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.mapper.CategoriaMapperRest;
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

import static com.jeffersonvilla.emazon.stock.infraestructura.rest.util.Constantes.*;

@Tag(name = "Categoria API", description = "Operaciones relacionadas con las categorías")
@RequiredArgsConstructor
@RestController
@RequestMapping("categoria")
public class CategoriaController {

    private final ICategoriaServicePort categoriaApi;
    private final CategoriaMapperRest mapper;

    @Operation(summary = "Listar categorías",
            description = "Obtiene una lista de categorías con paginación y ordenamiento.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de categorías obtenida exitosamente",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ListarCategoriaResponseDto.class)))),
            @ApiResponse(responseCode = "400", description = "Parámetros de solicitud inválidos",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("listar")
    public ResponseEntity<List<ListarCategoriaResponseDto>> listarCategorias(
            @RequestParam(defaultValue = PAGINA_DEFAULT) int pagina,
            @RequestParam(defaultValue = TAMANO_DEFAULT) int tamano,
            @RequestParam(defaultValue = ORDEN_DEFAULT) String orden
    ){

        return new ResponseEntity<>(
                categoriaApi.listarCategoria(pagina, tamano, orden)
                        .stream()
                        .map(mapper::categoriaToListarCategoriaResponseDto)
                        .toList(),
                HttpStatus.OK
        );
    }

    @Operation(summary = "Crear nueva categoría",
            description = "Crea una nueva categoría con los datos suministrados en el body.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoría creada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CrearCategoriaResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("crear")
    public ResponseEntity<CrearCategoriaResponseDto> crearCategoria(
            @Valid
            @RequestBody
            CrearCategoriaRequestDto categoriaDto
    ){

        return new ResponseEntity<>(
                mapper.categoriaToCrearCategoriaResponseDto(
                        categoriaApi.crearCategoria(
                                mapper.crearCategoriaRequestDtoToCategoria(categoriaDto)
                        )
                ),
                HttpStatus.CREATED
        );
    }
}
