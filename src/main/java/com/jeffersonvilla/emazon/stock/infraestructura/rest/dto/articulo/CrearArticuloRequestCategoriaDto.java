package com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.articulo;

import jakarta.validation.constraints.NotNull;

public record CrearArticuloRequestCategoriaDto (
        @NotNull Long categoriaId) {
}
