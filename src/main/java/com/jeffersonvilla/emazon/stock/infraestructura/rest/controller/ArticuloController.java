package com.jeffersonvilla.emazon.stock.infraestructura.rest.controller;

import com.jeffersonvilla.emazon.stock.dominio.api.IArticuloServicePort;
import com.jeffersonvilla.emazon.stock.dominio.api.ICategoriaServicePort;
import com.jeffersonvilla.emazon.stock.dominio.api.IMarcaServicePort;
import com.jeffersonvilla.emazon.stock.dominio.modelo.Articulo;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.articulo.CrearArticuloRequestDto;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.articulo.CrearArticuloResponseDto;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.mapper.ArticuloMapperRest;
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

@Tag(name = "Articulo API", description = "Operaciones relacionadas con los artículos")
@RequiredArgsConstructor
@RestController
@RequestMapping("articulo")
public class ArticuloController {

    private final IArticuloServicePort articuloApi;
    private final IMarcaServicePort marcaApi;
    private final ICategoriaServicePort categoriaApi;
    private final ArticuloMapperRest mapper;

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
