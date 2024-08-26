package com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.categoria;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.TAMANO_MAXIMO_DESCRIPCION_CATEGORIA;
import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.TAMANO_MAXIMO_NOMBRE_CATEGORIA;

public record CrearCategoriaRequestDto(
        @NotNull @Size(max = TAMANO_MAXIMO_NOMBRE_CATEGORIA) String nombre,
        @NotNull @Size(max = TAMANO_MAXIMO_DESCRIPCION_CATEGORIA) String descripcion) {

}
