package com.jeffersonvilla.emazon.stock.dominio.modelo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoriaTests {

    @Test
    void testCategoriaCreadaConExito() {
        Categoria categoria = new Categoria(1L, "Electrónica",
                "Categoría de dispositivos electrónicos");
        assertEquals(1L, categoria.getId());
        assertEquals("Electrónica", categoria.getNombre());
        assertEquals("Categoría de dispositivos electrónicos", categoria.getDescripcion());
    }

}
