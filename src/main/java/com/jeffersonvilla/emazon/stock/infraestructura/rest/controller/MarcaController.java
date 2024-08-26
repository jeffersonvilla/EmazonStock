package com.jeffersonvilla.emazon.stock.infraestructura.rest.controller;

import com.jeffersonvilla.emazon.stock.dominio.api.IMarcaServicePort;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.categoria.CrearCategoriaResponseDto;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.marca.CrearMarcaRequestDto;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.marca.CrearMarcaResponseDto;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.mapper.MarcaMapperRest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Marca API", description = "Operaciones relacionadas con las marcas")
@RequiredArgsConstructor
@RestController
@RequestMapping("marca")
public class MarcaController {

    private final IMarcaServicePort marcaApi;
    private final MarcaMapperRest mapper;

    @Operation(summary = "Crear nueva marca",
            description = "Crea una nueva marca con los datos suministrados en el body.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Marca creada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CrearCategoriaResponseDto.class))),
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
