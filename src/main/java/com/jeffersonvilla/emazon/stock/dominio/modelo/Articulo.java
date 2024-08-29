package com.jeffersonvilla.emazon.stock.dominio.modelo;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class Articulo {

    private final Long id;
    private final String nombre;
    private final String descripcion;
    private final Integer cantidad;
    private final BigDecimal precio;
    private Marca marca;
    private final Set<Categoria> categorias;

    public Articulo(Long id,
                    String nombre,
                    String descripcion,
                    Integer cantidad,
                    BigDecimal precio,
                    Marca marca,
                    Set<Categoria> categorias) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precio = precio;
        this.marca = marca;
        this.categorias = (categorias != null)? categorias : new HashSet<>();
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

    public Integer getCantidad() {
        return cantidad;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public Marca getMarca() {
        return marca;
    }

    public Set<Categoria> getCategorias() {
        return categorias;
    }

    public void setMarca(Marca marca){
        this.marca = marca;
    }

    public void agregarCategoria(Categoria categoria){
        this.categorias.add(categoria);
    }

}
