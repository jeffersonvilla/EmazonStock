package com.jeffersonvilla.emazon.stock.infraestructura.rest.excepciones;

import com.jeffersonvilla.emazon.stock.dominio.excepciones.BadRequestException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.NoEncontradoException;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ManejadorExcepcionesTest {

    private final ManejadorExcepciones manejadorExcepciones = new ManejadorExcepciones();

    @Test
    void testHandleBadRequestException() {
        String mensajeError = "BadRequestException";
        BadRequestException excepcion = new BadRequestException(mensajeError);

        ResponseEntity<RespuestaError> response = manejadorExcepciones
                .handleBadRequestException(excepcion);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST.toString(), response.getBody().status());
        assertEquals(mensajeError, response.getBody().message());
    }



    @Test
    void testHandleNoEncontradoException() {
        String mensajeError = "No encontrado";
        NoEncontradoException excepcion = new NoEncontradoException(mensajeError);

        ResponseEntity<RespuestaError> response = manejadorExcepciones
                .handleNoEncontradoException(excepcion);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND.toString(), response.getBody().status());
        assertEquals(mensajeError, response.getBody().message());
    }


    @Test
    void testHandleIllegalStateException() {
        String mensajeError = "Error interno";
        IllegalStateException excepcion = new IllegalStateException(mensajeError);

        ResponseEntity<RespuestaError> response = manejadorExcepciones
                .handleIllegalStateException(excepcion);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.toString(), response.getBody().status());
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