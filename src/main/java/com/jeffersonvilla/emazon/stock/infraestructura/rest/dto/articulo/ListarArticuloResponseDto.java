package com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.articulo;

import com.jeffersonvilla.emazon.stock.dominio.modelo.Marca;

import java.math.BigDecimal;
import java.util.Set;

public record ListarArticuloResponseDto(
        Long id,
        String nombre,
        String descripcion,
        Integer cantidad,
        BigDecimal precio,
        Marca marca,
        Set<ListarArticuloReponseCategoriaDto> categorias
) {
}
