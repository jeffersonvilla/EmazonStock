package com.jeffersonvilla.emazon.stock.infraestructura.rest.dto.articulo;

public record AumentarCantidadStockResponseDto(Long idArticulo, String nombre, Integer cantidad) {
}
