package com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.marca;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.TAMANO_MAXIMO_DESCRIPCION_MARCA;
import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.TAMANO_MAXIMO_NOMBRE_MARCA;

public record CrearMarcaRequestDto(
        @NotBlank @Size(max = TAMANO_MAXIMO_NOMBRE_MARCA) String nombre,
        @NotBlank @Size(max = TAMANO_MAXIMO_DESCRIPCION_MARCA) String descripcion) {
}
