package com.jeffersonvilla.emazon.stock.infraestructura.rest.controller;

import com.jeffersonvilla.emazon.stock.dominio.api.ICategoriaServicePort;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.CrearCategoriaRequestDto;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.CrearCategoriaResponseDto;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.mapper.CategoriaMapperRest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("categoria")
public class CategoriaController {

    private final ICategoriaServicePort crearCategoriaApi;
    private final CategoriaMapperRest mapper;

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
