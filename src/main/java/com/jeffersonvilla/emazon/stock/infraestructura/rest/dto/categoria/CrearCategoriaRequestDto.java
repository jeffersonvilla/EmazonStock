package com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.categoria;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.TAMANO_MAXIMO_DESCRIPCION_CATEGORIA;
import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.TAMANO_MAXIMO_NOMBRE_CATEGORIA;

public record CrearCategoriaRequestDto(
        @NotBlank @Size(max = TAMANO_MAXIMO_NOMBRE_CATEGORIA) String nombre,
        @NotBlank @Size(max = TAMANO_MAXIMO_DESCRIPCION_CATEGORIA) String descripcion) {

}
