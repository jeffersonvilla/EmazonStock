package com.jeffersonvilla.emazon.stock.infraestructura.rest.excepciones;

import com.jeffersonvilla.emazon.stock.dominio.excepciones.CreacionCategoriaException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.DescriptionNoValidaException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.NombreNoValidoException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class ManejadorExcepcionesTest {

    private final ManejadorExcepciones manejadorExcepciones = new ManejadorExcepciones();

    @Test
    void testHandleNombreNoValidoException() {
        String mensajeError = "El nombre no es válido";
        NombreNoValidoException excepcion = new NombreNoValidoException(mensajeError);

        ResponseEntity<RespuestaError> response = manejadorExcepciones
                .handleNombreNoValidoException(excepcion);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST.toString(), response.getBody().status());
        assertEquals(mensajeError, response.getBody().message());
    }

    @Test
    void testHandleDescriptionNoValidaException() {
        String mensajeError = "La descripción no es válida";
        DescriptionNoValidaException excepcion = new DescriptionNoValidaException(mensajeError);

        ResponseEntity<RespuestaError> response = manejadorExcepciones
                .handleDescriptionNoValidaException(excepcion);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST.toString(), response.getBody().status());
        assertEquals(mensajeError, response.getBody().message());
    }

    @Test
    void testHandleCreacionCategoriaException() {
        String mensajeError = "Error al crear la categoría";
        CreacionCategoriaException excepcion = new CreacionCategoriaException(mensajeError);

        ResponseEntity<RespuestaError> response = manejadorExcepciones
                .handleCreacionCategoriaException(excepcion);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST.toString(), response.getBody().status());
        assertEquals(mensajeError, response.getBody().message());
    }
}