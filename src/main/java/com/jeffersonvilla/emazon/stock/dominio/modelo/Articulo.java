package com.jeffersonvilla.emazon.stock.dominio.modelo;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class Articulo {

    private Long id;
    private String nombre;
    private String descripcion;
    private Integer cantidad;
    private BigDecimal precio;
    private Marca marca;
    private Set<Categoria> categorias;
    private Integer version;

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

    public Integer getVersion() {
        return version;
    }

    public void aumentarCantidad(Integer cantidadNueva) {
        this.cantidad += cantidadNueva;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public void agregarCategoria(Categoria categoria){
        this.categorias.add(categoria);
    }

    private Articulo(ArticuloBuilder builder){
        this.id = builder.id;
        this.nombre = builder.nombre;
        this.descripcion = builder.descripcion;
        this.cantidad = builder.cantidad;
        this.precio = builder.precio;
        this.marca = builder.marca;
        this.categorias = (builder.categorias != null)? builder.categorias : new HashSet<>();
        this.version = builder.version;
    }

    public static class ArticuloBuilder{

        private Long id = null;
        private String nombre = null;
        private String descripcion = null;
        private Integer cantidad = null;
        private BigDecimal precio = null;
        private Marca marca = null;
        private Set<Categoria> categorias = null;
        private Integer version = null;

        public Articulo build(){
            return new Articulo(this);
        }

        public ArticuloBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public ArticuloBuilder setNombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public ArticuloBuilder setDescripcion(String descripcion) {
            this.descripcion = descripcion;
            return this;
        }

        public ArticuloBuilder setCantidad(Integer cantidad) {
            this.cantidad = cantidad;
            return this;
        }

        public ArticuloBuilder setPrecio(BigDecimal precio) {
            this.precio = precio;
            return this;
        }

        public ArticuloBuilder setMarca(Marca marca) {
            this.marca = marca;
            return this;
        }

        public ArticuloBuilder setCategorias(Set<Categoria> categorias) {
            this.categorias = categorias;
            return this;
        }

        public ArticuloBuilder setVersion(Integer version) {
            this.version = version;
            return this;
        }
    }

}
