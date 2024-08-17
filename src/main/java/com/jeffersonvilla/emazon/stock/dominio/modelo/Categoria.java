package com.jeffersonvilla.emazon.stock.dominio.modelo;

import com.jeffersonvilla.emazon.stock.dominio.excepciones.DescriptionNoValidaException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.NombreNoValidoException;
import com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorGenerales;

public class Categoria {

    private final Long id;
    private final String nombre;
    private final String descripcion;

    public Categoria(Long id, String nombre, String descripcion){
        if(nombre == null){
            throw new NombreNoValidoException(MensajesErrorGenerales.NOMBRE_NULL);
        }
        if(nombre.isBlank()){
            throw new NombreNoValidoException(MensajesErrorGenerales.NOMBRE_VACIO);
        }
        if(descripcion == null){
            throw new DescriptionNoValidaException(MensajesErrorGenerales.DESCRIPCION_NULL);
        }
        if(descripcion.isBlank()){
            throw new DescriptionNoValidaException(MensajesErrorGenerales.DESCRIPCION_VACIA);
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
