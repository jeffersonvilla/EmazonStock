package com.jeffersonvilla.emazon.stock.dominio.modelo;

import com.jeffersonvilla.emazon.stock.dominio.excepciones.DescriptionCategoriaNoValidaException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.NombreCategoriaNoValidoException;
import com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorGenerales;

public class Categoria {

    private final Long id;
    private final String nombre;
    private final String descripcion;

    public Categoria(Long id, String nombre, String descripcion){
        if(nombre == null){
            throw new NombreCategoriaNoValidoException(MensajesErrorGenerales.NOMBRE_NULL);
        }
        if(nombre.isBlank()){
            throw new NombreCategoriaNoValidoException(MensajesErrorGenerales.NOMBRE_VACIO);
        }
        if(descripcion == null){
            throw new DescriptionCategoriaNoValidaException(MensajesErrorGenerales.DESCRIPCION_NULL);
        }
        if(descripcion.isBlank()){
            throw new DescriptionCategoriaNoValidaException(MensajesErrorGenerales.DESCRIPCION_VACIA);
        }
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
