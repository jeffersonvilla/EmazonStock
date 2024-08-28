package com.jeffersonvilla.emazon.stock.dominio.util;

import com.jeffersonvilla.emazon.stock.dominio.excepciones.DescripcionNoValidaException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.NombreNoValidoException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidacionCamposModeloTest {

    @Test
    void testNombreNullLanzaExcepcion() {
        Exception exception = assertThrows(NombreNoValidoException.class, () -> {
            ValidacionCamposModelo.validarNombreNoNuloNiVacio(null);
        });
        assertEquals(MensajesErrorGenerales.NOMBRE_NULL, exception.getMessage());
    }

    @Test
    void testNombreVacioLanzaExcepcion() {
        Exception exception = assertThrows(NombreNoValidoException.class, () -> {
            ValidacionCamposModelo.validarNombreNoNuloNiVacio(" ");
        });
        assertEquals(MensajesErrorGenerales.NOMBRE_VACIO, exception.getMessage());
    }

    @Test
    void testDescripcionNullLanzaExcepcion() {
        Exception exception = assertThrows(DescripcionNoValidaException.class, () -> {
            ValidacionCamposModelo.validarDescripcionNoNuloNiVacio(null);
        });
        assertEquals(MensajesErrorGenerales.DESCRIPCION_NULL, exception.getMessage());
    }

    @Test
    void testDescripcionVaciaLanzaExcepcion() {
        Exception exception = assertThrows(DescripcionNoValidaException.class, () -> {
            ValidacionCamposModelo.validarDescripcionNoNuloNiVacio(" ");
        });
        assertEquals(MensajesErrorGenerales.DESCRIPCION_VACIA, exception.getMessage());
    }
}