package com.jeffersonvilla.emazon.stock.infraestructura.rest.excepciones;

import com.jeffersonvilla.emazon.stock.dominio.excepciones.articulo.CreacionArticuloException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.articulo.ListarArticuloException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.categoria.CategoriaNoExisteException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.general.*;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.categoria.CreacionCategoriaException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.categoria.ListarCategoriaException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.marca.CreacionMarcaException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.marca.ListarMarcaException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.marca.MarcaNoExisteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ManejadorExcepciones {

    @ExceptionHandler(IdNuloException.class)
    public ResponseEntity<RespuestaError> handleIdNuloException(
            IdNuloException ex){

        return ResponseEntity.badRequest().body(
                new RespuestaError(HttpStatus.BAD_REQUEST.toString(), ex.getMessage()));
    }

    @ExceptionHandler(NombreNoValidoException.class)
    public ResponseEntity<RespuestaError> handleNombreNoValidoException(
            NombreNoValidoException ex){

        return ResponseEntity.badRequest().body(
                new RespuestaError(HttpStatus.BAD_REQUEST.toString(), ex.getMessage()));
    }

    @ExceptionHandler(DescripcionNoValidaException.class)
    public ResponseEntity<RespuestaError> handleDescripcionNoValidaException(
            DescripcionNoValidaException ex){

        return ResponseEntity.badRequest().body(
                new RespuestaError(HttpStatus.BAD_REQUEST.toString(), ex.getMessage()));
    }

    @ExceptionHandler(CantidadNoValidaException.class)
    public ResponseEntity<RespuestaError> handleCantidadNoValidaException(
            CantidadNoValidaException ex){

        return ResponseEntity.badRequest().body(
                new RespuestaError(HttpStatus.BAD_REQUEST.toString(), ex.getMessage()));
    }

    @ExceptionHandler(PrecioNoValidoException.class)
    public ResponseEntity<RespuestaError> handlePrecioNoValidoException(
            PrecioNoValidoException ex){

        return ResponseEntity.badRequest().body(
                new RespuestaError(HttpStatus.BAD_REQUEST.toString(), ex.getMessage()));
    }

    @ExceptionHandler(CreacionCategoriaException.class)
    public ResponseEntity<RespuestaError> handleCreacionCategoriaException(
            CreacionCategoriaException ex){

        return ResponseEntity.badRequest().body(
                new RespuestaError(HttpStatus.BAD_REQUEST.toString(), ex.getMessage()));
    }

    @ExceptionHandler(ListarCategoriaException.class)
    public ResponseEntity<RespuestaError> handleListarCategoriaException(
            ListarCategoriaException ex){

        return ResponseEntity.badRequest().body(
                new RespuestaError(HttpStatus.BAD_REQUEST.toString(), ex.getMessage()));
    }

    @ExceptionHandler(CategoriaNoExisteException.class)
    public ResponseEntity<RespuestaError> handleCategoriaNoExisteException(
            CategoriaNoExisteException ex){

        return new ResponseEntity<>(
                new RespuestaError(HttpStatus.NOT_FOUND.toString(), ex.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(CreacionMarcaException.class)
    public ResponseEntity<RespuestaError> handleCreacionMarcaException(
            CreacionMarcaException ex){

        return ResponseEntity.badRequest().body(
                new RespuestaError(HttpStatus.BAD_REQUEST.toString(), ex.getMessage()));
    }

    @ExceptionHandler(ListarMarcaException.class)
    public ResponseEntity<RespuestaError> handleListarMarcaException(
            ListarMarcaException ex){

        return ResponseEntity.badRequest().body(
                new RespuestaError(HttpStatus.BAD_REQUEST.toString(), ex.getMessage()));
    }

    @ExceptionHandler(MarcaNoExisteException.class)
    public ResponseEntity<RespuestaError> handleMarcaNoExisteException(
            MarcaNoExisteException ex){

        return new ResponseEntity<>(
                new RespuestaError(HttpStatus.NOT_FOUND.toString(), ex.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(CreacionArticuloException.class)
    public ResponseEntity<RespuestaError> handleCreacionArticuloException(
            CreacionArticuloException ex){

        return ResponseEntity.badRequest().body(
                new RespuestaError(HttpStatus.BAD_REQUEST.toString(), ex.getMessage()));
    }

    @ExceptionHandler(ListarArticuloException.class)
    public ResponseEntity<RespuestaError> handleListarArticuloException(
            ListarArticuloException ex){

        return ResponseEntity.badRequest().body(
                new RespuestaError(HttpStatus.BAD_REQUEST.toString(), ex.getMessage()));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<RespuestaError> handleIllegalStateException(
            IllegalStateException ex){

        return ResponseEntity.badRequest().body(
                new RespuestaError(HttpStatus.BAD_REQUEST.toString(), ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RespuestaConVariosErrores> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex) {

        Map<String, String> mapaErrores = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String campoNombre = ((FieldError) error).getField();
            String mensajeError = error.getDefaultMessage();
            mapaErrores.put(campoNombre, mensajeError);
        });

        RespuestaConVariosErrores errores =
                new RespuestaConVariosErrores(HttpStatus.BAD_REQUEST.toString(), mapaErrores);

        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
    }
}
