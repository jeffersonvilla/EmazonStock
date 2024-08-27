package com.jeffersonvilla.emazon.stock.dominio.modelo;

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

}