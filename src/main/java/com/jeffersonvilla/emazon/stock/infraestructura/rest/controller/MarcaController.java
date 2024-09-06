package com.jeffersonvilla.emazon.stock.infraestructura.rest.controller;

import com.jeffersonvilla.emazon.stock.dominio.api.IMarcaServicePort;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.marca.CrearMarcaResponseDto;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.marca.ListarMarcaResponseDto;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.marca.CrearMarcaRequestDto;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.mapper.MarcaMapperRest;
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

@Tag(name = "Marca API", description = "Operaciones relacionadas con las marcas")
@RequiredArgsConstructor
@RestController
@RequestMapping("marca")
public class MarcaController {

    private final IMarcaServicePort marcaApi;
    private final MarcaMapperRest mapper;

    @Operation(summary = "Listar marcas",
            description = "Obtiene una lista de marcas con paginación y ordenamiento.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de marcas obtenida exitosamente",
                    content = @Content(mediaType = "application/json",
                        array = @ArraySchema(schema = @Schema(implementation = ListarMarcaResponseDto.class)))),
            @ApiResponse(responseCode = "400", description = "Parámetros de solicitud inválidos",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("listar")
    public ResponseEntity<List<ListarMarcaResponseDto>> listarMarcas(
            @RequestParam(defaultValue = PAGINA_DEFAULT) int pagina,
            @RequestParam(defaultValue = TAMANO_DEFAULT) int tamano,
            @RequestParam(defaultValue = ORDEN_DEFAULT) String orden
    ){

        return new ResponseEntity<>(
                marcaApi.listarMarca(pagina, tamano, orden)
                        .stream()
                        .map(mapper::marcaToListarMarcaResponseDto)
                        .toList(),
                HttpStatus.OK
        );
    }
    
    @Operation(summary = "Crear nueva marca",
            description = "Crea una nueva marca con los datos suministrados en el body.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Marca creada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CrearMarcaResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("crear")
    public ResponseEntity<CrearMarcaResponseDto> crearMarca(
            @Valid
            @RequestBody
            CrearMarcaRequestDto marcaDto
    ){

        return new ResponseEntity<>(
                mapper.marcaToCrearMarcaResponseDto(
                        marcaApi.crearMarca(
                                mapper.crearMarcaRequestDtoToMarca(marcaDto)
                        )
                ),
                HttpStatus.CREATED
        );
    }
}
