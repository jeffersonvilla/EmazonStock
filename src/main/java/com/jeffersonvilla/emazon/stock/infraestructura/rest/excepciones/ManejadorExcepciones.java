package com.jeffersonvilla.emazon.stock.infraestructura.rest.excepciones;

import com.jeffersonvilla.emazon.stock.dominio.excepciones.DescripcionNoValidaException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.NombreNoValidoException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.categoria.CreacionCategoriaException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.categoria.ListarCategoriaException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.marca.CreacionMarcaException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.marca.ListarMarcaException;
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
