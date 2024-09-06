package com.jeffersonvilla.emazon.stock.infraestructura.rest.excepciones;

import com.jeffersonvilla.emazon.stock.dominio.excepciones.articulo.CreacionArticuloException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.articulo.ListarArticuloException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.categoria.CategoriaNoExisteException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.categoria.CreacionCategoriaException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.categoria.ListarCategoriaException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.general.CantidadNoValidaException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.general.DescripcionNoValidaException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.general.IdNuloException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.general.NombreNoValidoException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.general.PrecioNoValidoException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.marca.CreacionMarcaException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.marca.ListarMarcaException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.marca.MarcaNoExisteException;
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
    void testHandleIdNuloException() {
        String mensajeError = "El ID no es válido";
        IdNuloException excepcion = new IdNuloException(mensajeError);

        ResponseEntity<RespuestaError> response = manejadorExcepciones
                .handleIdNuloException(excepcion);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST.toString(), response.getBody().status());
        assertEquals(mensajeError, response.getBody().message());
    }

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
    void testHandleDescripcionNoValidaException() {
        String mensajeError = "La descripción no es válida";
        DescripcionNoValidaException excepcion = new DescripcionNoValidaException(mensajeError);

        ResponseEntity<RespuestaError> response = manejadorExcepciones
                .handleDescripcionNoValidaException(excepcion);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST.toString(), response.getBody().status());
        assertEquals(mensajeError, response.getBody().message());
    }

    @Test
    void testHandleCantidadNoValidaException() {
        String mensajeError = "La cantidad no es válida";
        CantidadNoValidaException excepcion = new CantidadNoValidaException(mensajeError);

        ResponseEntity<RespuestaError> response = manejadorExcepciones
                .handleCantidadNoValidaException(excepcion);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST.toString(), response.getBody().status());
        assertEquals(mensajeError, response.getBody().message());
    }

    @Test
    void testHandlePrecioNoValidoException() {
        String mensajeError = "El precio no es válido";
        PrecioNoValidoException excepcion = new PrecioNoValidoException(mensajeError);

        ResponseEntity<RespuestaError> response = manejadorExcepciones
                .handlePrecioNoValidoException(excepcion);

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
    void testHandleCategoriaNoExisteException() {
        String mensajeError = "La categoría no existe";
        CategoriaNoExisteException excepcion = new CategoriaNoExisteException(mensajeError);

        ResponseEntity<RespuestaError> response = manejadorExcepciones
                .handleCategoriaNoExisteException(excepcion);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND.toString(), response.getBody().status());
        assertEquals(mensajeError, response.getBody().message());
    }

    @Test
    void testHandleCreacionMarcaException() {
        String mensajeError = "Error al crear la marca";
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
    void testHandleListarMarcaException() {
        String mensajeError = "Error al listar las marcas";
        ListarMarcaException excepcion = new ListarMarcaException(mensajeError);

        ResponseEntity<RespuestaError> response = manejadorExcepciones
                .handleListarMarcaException(excepcion);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST.toString(), response.getBody().status());
        assertEquals(mensajeError, response.getBody().message());
    }

    @Test
    void testHandleMarcaNoExisteException() {
        String mensajeError = "La marca no existe";
        MarcaNoExisteException excepcion = new MarcaNoExisteException(mensajeError);

        ResponseEntity<RespuestaError> response = manejadorExcepciones
                .handleMarcaNoExisteException(excepcion);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND.toString(), response.getBody().status());
        assertEquals(mensajeError, response.getBody().message());
    }

    @Test
    void testHandleCreacionArticuloException() {
        String mensajeError = "Error al crear el articulo";
        CreacionArticuloException excepcion = new CreacionArticuloException(mensajeError);

        ResponseEntity<RespuestaError> response = manejadorExcepciones
                .handleCreacionArticuloException(excepcion);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST.toString(), response.getBody().status());
        assertEquals(mensajeError, response.getBody().message());
    }

    @Test
    void testHandleListarArticuloException() {
        String mensajeError = "Error al listar los articulos";
        ListarArticuloException excepcion = new ListarArticuloException(mensajeError);

        ResponseEntity<RespuestaError> response = manejadorExcepciones
                .handleListarArticuloException(excepcion);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST.toString(), response.getBody().status());
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