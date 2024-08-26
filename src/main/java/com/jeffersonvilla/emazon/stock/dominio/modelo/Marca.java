package com.jeffersonvilla.emazon.stock.dominio.modelo;

import com.jeffersonvilla.emazon.stock.dominio.excepciones.marca.DescripcionMarcaNoValidaException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.marca.NombreMarcaNoValidoException;
import com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorGenerales;

public class Marca {

    private final Long id;
    private final String nombre;
    private final String descripcion;

    public Marca(Long id, String nombre, String descripcion){
        if(nombre == null){
            throw new NombreMarcaNoValidoException(MensajesErrorGenerales.NOMBRE_NULL);
        }
        if(nombre.isBlank()){
            throw new NombreMarcaNoValidoException(MensajesErrorGenerales.NOMBRE_VACIO);
        }
        if(descripcion == null){
            throw new DescripcionMarcaNoValidaException(MensajesErrorGenerales.DESCRIPCION_NULL);
        }
        if(descripcion.isBlank()){
            throw new DescripcionMarcaNoValidaException(MensajesErrorGenerales.DESCRIPCION_VACIA);
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
