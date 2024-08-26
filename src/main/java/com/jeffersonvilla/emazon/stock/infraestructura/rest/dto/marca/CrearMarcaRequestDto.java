package com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.marca;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.TAMANO_MAXIMO_DESCRIPCION_MARCA;
import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.TAMANO_MAXIMO_NOMBRE_MARCA;

public record CrearMarcaRequestDto(
        @NotNull @Size(max = TAMANO_MAXIMO_NOMBRE_MARCA) String nombre,
        @NotNull @Size(max = TAMANO_MAXIMO_DESCRIPCION_MARCA) String descripcion) {
}
