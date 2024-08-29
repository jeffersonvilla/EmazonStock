package com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.articulo;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.Set;

import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.*;

public record CrearArticuloRequestDto(
        @NotNull @Size(max = TAMANO_MAXIMO_NOMBRE_ARTICULO)
        String nombre,

        @NotNull @Size(max = TAMANO_MAXIMO_DESCRIPCION_ARTICULO)
        String descripcion,

        @NotNull @Min(value = CANTIDAD_STOCK_MINIMA) Integer cantidad,
        @NotNull @Min(value = PRECIO_MINIMO)  BigDecimal precio,
        @NotNull Long marcaId,

        @NotNull
        @Size(min = CANTIDAD_MINIMA_CATEGORIAS_POR_ARTICULO,
            max = CANTIDAD_MAXIMA_CATEGORIAS_POR_ARTICULO)
        Set<CrearArticuloRequestCategoriaDto> categorias
) {
}
