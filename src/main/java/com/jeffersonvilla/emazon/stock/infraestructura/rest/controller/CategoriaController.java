package com.jeffersonvilla.emazon.stock.infraestructura.rest.controller;

import com.jeffersonvilla.emazon.stock.dominio.api.ICategoriaServicePort;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.CrearCategoriaRequestDto;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.CrearCategoriaResponseDto;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.mapper.CategoriaMapperRest;
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

@Tag(name = "Categoria API", description = "Operaciones relacionadas con las categorías")
@RequiredArgsConstructor
@RestController
@RequestMapping("categoria")
public class CategoriaController {

    private final ICategoriaServicePort crearCategoriaApi;
    private final CategoriaMapperRest mapper;

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
                        crearCategoriaApi.crearCategoria(
                                mapper.crearCategoriaRequestDtoToCategoria(categoriaDto)
                        )
                ),
                HttpStatus.CREATED
        );
    }
}
