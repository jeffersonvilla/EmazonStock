package com.jeffersonvilla.emazon.stock.infraestructura.rest.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CrearCategoriaRequestDto(
        @NotNull @Size(max = 50) String nombre,
        @NotNull @Size(max = 90) String descripcion) {

}
