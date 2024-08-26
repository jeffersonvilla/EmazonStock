package com.jeffersonvilla.emazon.stock.dominio.modelo;

import com.jeffersonvilla.emazon.stock.dominio.excepciones.marca.DescripcionMarcaNoValidaException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.marca.NombreMarcaNoValidoException;
import com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorGenerales;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MarcaTest {

    @Test
    void testMarcaCreadaConExito() {
        String nombreMarca = "Marca A";
        String descripcionMarca = "Marca de dispositivos electrónicos";
        Marca marca = new Marca(1L, nombreMarca, descripcionMarca);

        assertEquals(1L, marca.getId());
        assertEquals(nombreMarca, marca.getNombre());
        assertEquals(descripcionMarca, marca.getDescripcion());
    }

    @Test
    void testNombreNullLanzaExcepcion() {
        Exception exception = assertThrows(NombreMarcaNoValidoException.class, () -> {
            new Marca(1L, null, "Descripción válida");
        });
        assertEquals(MensajesErrorGenerales.NOMBRE_NULL, exception.getMessage());
    }

    @Test
    void testNombreVacioLanzaExcepcion() {
        Exception exception = assertThrows(NombreMarcaNoValidoException.class, () -> {
            new Marca(1L, "", "Descripción válida");
        });
        assertEquals(MensajesErrorGenerales.NOMBRE_VACIO, exception.getMessage());
    }

    @Test
    void testDescripcionNullLanzaExcepcion() {
        Exception exception = assertThrows(DescripcionMarcaNoValidaException.class, () -> {
            new Marca(1L, "Nombre válido", null);
        });
        assertEquals(MensajesErrorGenerales.DESCRIPCION_NULL, exception.getMessage());
    }

    @Test
    void testDescripcionVaciaLanzaExcepcion() {
        Exception exception = assertThrows(DescripcionMarcaNoValidaException.class, () -> {
            new Marca(1L, "Nombre válido", "");
        });
        assertEquals(MensajesErrorGenerales.DESCRIPCION_VACIA, exception.getMessage());
    }
}