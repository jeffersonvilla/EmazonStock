package com.jeffersonvilla.emazon.stock.dominio.modelo;

import com.jeffersonvilla.emazon.stock.dominio.excepciones.categoria.DescriptionCategoriaNoValidaException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.categoria.NombreCategoriaNoValidoException;
import com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorGenerales;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CategoriaTests {

    @Test
    void testCategoriaCreadaConExito() {
        Categoria categoria = new Categoria(1L, "Electrónica",
                "Categoría de dispositivos electrónicos");
        assertEquals(1L, categoria.getId());
        assertEquals("Electrónica", categoria.getNombre());
        assertEquals("Categoría de dispositivos electrónicos", categoria.getDescripcion());
    }

    @Test
    void testNombreNullLanzaExcepcion() {
        Exception exception = assertThrows(NombreCategoriaNoValidoException.class, () -> {
            new Categoria(1L, null, "Descripción válida");
        });
        assertEquals(MensajesErrorGenerales.NOMBRE_NULL, exception.getMessage());
    }

    @Test
    void testNombreVacioLanzaExcepcion() {
        Exception exception = assertThrows(NombreCategoriaNoValidoException.class, () -> {
            new Categoria(1L, "", "Descripción válida");
        });
        assertEquals(MensajesErrorGenerales.NOMBRE_VACIO, exception.getMessage());
    }

    @Test
    void testDescripcionNullLanzaExcepcion() {
        Exception exception = assertThrows(DescriptionCategoriaNoValidaException.class, () -> {
            new Categoria(1L, "Nombre válido", null);
        });
        assertEquals(MensajesErrorGenerales.DESCRIPCION_NULL, exception.getMessage());
    }

    @Test
    void testDescripcionVaciaLanzaExcepcion() {
        Exception exception = assertThrows(DescriptionCategoriaNoValidaException.class, () -> {
            new Categoria(1L, "Nombre válido", "");
        });
        assertEquals(MensajesErrorGenerales.DESCRIPCION_VACIA, exception.getMessage());
    }

}
