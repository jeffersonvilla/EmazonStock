package com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.articulo;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.CANTIDAD_AUMENTO_MINIMO_CANTIDAD_STOCK;

public record AumentarCantidadStockRequestDto(
        @NotNull Long idArticulo,
        @NotNull @Min(CANTIDAD_AUMENTO_MINIMO_CANTIDAD_STOCK) Integer cantidad) {
}
