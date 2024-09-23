package com.jeffersonvilla.emazon.stock.dominio.modelo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class ArticuloTest {
    private Articulo articulo;
    private Marca marca;
    private Categoria categoria1;
    private Categoria categoria2;

    @BeforeEach
    void setUp() {
        marca = mock(Marca.class);
        categoria1 = mock(Categoria.class);
        categoria2 = mock(Categoria.class);

        Set<Categoria> categorias = new HashSet<>();
        categorias.add(categoria1);

        articulo = new Articulo.ArticuloBuilder()
                .setId(1L)
                .setNombre("Computador")
                .setDescripcion("Computador portátil")
                .setCantidad(10)
                .setPrecio(BigDecimal.valueOf(1200.0))
                .setMarca(marca)
                .setCategorias(categorias)
                .setVersion(1)
                .build();
    }

    @Test
    void testGetId() {
        assertEquals(1L, articulo.getId());
    }

    @Test
    void testGetNombre() {
        assertEquals("Computador", articulo.getNombre());
    }

    @Test
    void testGetDescripcion() {
        assertEquals("Computador portátil", articulo.getDescripcion());
    }

    @Test
    void testGetCantidad() {
        assertEquals(10, articulo.getCantidad());
    }

    @Test
    void testGetPrecio() {
        assertEquals(BigDecimal.valueOf(1200.00), articulo.getPrecio());
    }

    @Test
    void testGetMarca() {
        assertEquals(marca, articulo.getMarca());
    }

    @Test
    void testGetCategorias() {
        Set<Categoria> categorias = articulo.getCategorias();
        assertEquals(1, categorias.size());
        assertTrue(categorias.contains(categoria1));
    }

    @Test
    void testGetVersion(){
        assertEquals(1, articulo.getVersion());
    }

    @Test
    void testSetMarca() {
        Marca nuevaMarca = mock(Marca.class);
        articulo.setMarca(nuevaMarca);

        assertEquals(nuevaMarca, articulo.getMarca());
    }

    @Test
    void testAgregarCategoria() {
        articulo.agregarCategoria(categoria2);

        Set<Categoria> categorias = articulo.getCategorias();
        assertEquals(2, categorias.size());
        assertTrue(categorias.contains(categoria1));
        assertTrue(categorias.contains(categoria2));
    }

    @Test
    void testCrearArticuloConCategoriasNull() {
        Articulo articuloSinCategorias = new Articulo.ArticuloBuilder()
                .setId(1L)
                .setNombre("Laptop")
                .setDescripcion("Laptop gaming")
                .setCantidad(5)
                .setPrecio(BigDecimal.valueOf(1200.0))
                .setMarca(mock(Marca.class))
                .build();

        assertNotNull(articuloSinCategorias.getCategorias());
        assertTrue(articuloSinCategorias.getCategorias().isEmpty());
    }

    @Test
    void testAumentarCantidad(){
        int cantidadAumento = 5;
        int cantidadAnterior = articulo.getCantidad();
        int nuevaCantidad = cantidadAnterior + cantidadAumento;

        articulo.aumentarCantidad(cantidadAumento);

        assertEquals(nuevaCantidad, articulo.getCantidad());
    }
}