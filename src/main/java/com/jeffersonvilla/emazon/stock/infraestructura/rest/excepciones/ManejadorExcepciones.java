package com.jeffersonvilla.emazon.stock.infraestructura.rest.excepciones;

import com.jeffersonvilla.emazon.stock.dominio.excepciones.CreacionCategoriaException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.DescriptionNoValidaException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.NombreNoValidoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ManejadorExcepciones {

    @ExceptionHandler(NombreNoValidoException.class)
    public ResponseEntity<RespuestaError> handleNombreNoValidoException(
            NombreNoValidoException ex){

        return ResponseEntity.badRequest().body(
                new RespuestaError(HttpStatus.BAD_REQUEST.toString(), ex.getMessage()));
    }

    @ExceptionHandler(DescriptionNoValidaException.class)
    public ResponseEntity<RespuestaError> handleDescriptionNoValidaException(
            DescriptionNoValidaException ex){

        return ResponseEntity.badRequest().body(
                new RespuestaError(HttpStatus.BAD_REQUEST.toString(), ex.getMessage()));
    }

    @ExceptionHandler(CreacionCategoriaException.class)
    public ResponseEntity<RespuestaError> handleCreacionCategoriaException(
            CreacionCategoriaException ex){

        return ResponseEntity.badRequest().body(
                new RespuestaError(HttpStatus.BAD_REQUEST.toString(), ex.getMessage()));
    }
}
