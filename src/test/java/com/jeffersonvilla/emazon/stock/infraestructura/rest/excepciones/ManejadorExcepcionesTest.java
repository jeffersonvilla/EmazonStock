package com.jeffersonvilla.emazon.stock.infraestructura.rest.excepciones;

import com.jeffersonvilla.emazon.stock.dominio.excepciones.categoria.CreacionCategoriaException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.categoria.DescriptionCategoriaNoValidaException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.categoria.ListarCategoriaException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.categoria.NombreCategoriaNoValidoException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.marca.CreacionMarcaException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.marca.DescripcionMarcaNoValidaException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.marca.NombreMarcaNoValidoException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ManejadorExcepcionesTest {

    private final ManejadorExcepciones manejadorExcepciones = new ManejadorExcepciones();

    @Test
    void testHandleNombreCategoriaNoValidoException() {
        String mensajeError = "El nombre no es válido";
        NombreCategoriaNoValidoException excepcion = new NombreCategoriaNoValidoException(mensajeError);

        ResponseEntity<RespuestaError> response = manejadorExcepciones
                .handleNombreCategoriaNoValidoException(excepcion);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST.toString(), response.getBody().status());
        assertEquals(mensajeError, response.getBody().message());
    }

    @Test
    void testHandleDescriptionCategoriaNoValidaException() {
        String mensajeError = "La descripción no es válida";
        DescriptionCategoriaNoValidaException excepcion = new DescriptionCategoriaNoValidaException(mensajeError);

        ResponseEntity<RespuestaError> response = manejadorExcepciones
                .handleDescriptionCategoriaNoValidaException(excepcion);

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

    @Test
    void testHandleListarCategoriaException() {
        String mensajeError = "Error al listar las categorías";
        ListarCategoriaException excepcion = new ListarCategoriaException(mensajeError);

        ResponseEntity<RespuestaError> response = manejadorExcepciones
                .handleListarCategoriaException(excepcion);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST.toString(), response.getBody().status());
        assertEquals(mensajeError, response.getBody().message());
    }

    @Test
    void testHandleNombreMarcaNoValidoException() {
        String mensajeError = "El nombre no es válido";
        NombreMarcaNoValidoException excepcion = new NombreMarcaNoValidoException(mensajeError);

        ResponseEntity<RespuestaError> response = manejadorExcepciones
                .handleNombreMarcaNoValidoException(excepcion);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST.toString(), response.getBody().status());
        assertEquals(mensajeError, response.getBody().message());
    }

    @Test
    void testHandleDescriptionMarcaNoValidaException() {
        String mensajeError = "La descripción no es válida";
        DescripcionMarcaNoValidaException excepcion = new DescripcionMarcaNoValidaException(mensajeError);

        ResponseEntity<RespuestaError> response = manejadorExcepciones
                .handleDescripcionMarcaNoValidaException(excepcion);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST.toString(), response.getBody().status());
        assertEquals(mensajeError, response.getBody().message());
    }

    @Test
    void testHandleCreacionMarcaException() {
        String mensajeError = "Error al crear la categoría";
        CreacionMarcaException excepcion = new CreacionMarcaException(mensajeError);

        ResponseEntity<RespuestaError> response = manejadorExcepciones
                .handleCreacionMarcaException(excepcion);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST.toString(), response.getBody().status());
        assertEquals(mensajeError, response.getBody().message());
    }

    @Test
    void testHandleMethodArgumentNotValidException() {

        ObjectError error1 = new FieldError("nombreObjeto", "campo1", "mensaje error 1");
        ObjectError error2 = new FieldError("nombreObjeto", "campo2", "mensaje error 2");

        MethodArgumentNotValidException methodArgumentNotValidException =
                mock(MethodArgumentNotValidException.class);

        BindingResult bindingResult = mock(BindingResult.class);

        List<ObjectError> listaErrores = Arrays.asList(error1, error2);
        when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getAllErrors()).thenReturn(listaErrores);


        ResponseEntity<RespuestaConVariosErrores> response =
                manejadorExcepciones.handleMethodArgumentNotValidException(methodArgumentNotValidException);


        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        RespuestaConVariosErrores body = response.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.toString(), body.status());

        Map<String, String> errores = body.mensajes();
        assertEquals(2, errores.size());
        assertEquals("mensaje error 1", errores.get("campo1"));
        assertEquals("mensaje error 2", errores.get("campo2"));
    }
}