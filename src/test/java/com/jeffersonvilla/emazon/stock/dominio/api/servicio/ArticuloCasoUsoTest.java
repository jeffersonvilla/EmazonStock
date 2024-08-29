package com.jeffersonvilla.emazon.stock.dominio.api.servicio;

import com.jeffersonvilla.emazon.stock.dominio.excepciones.articulo.CreacionArticuloException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.general.CantidadNoValidaException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.general.DescripcionNoValidaException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.general.NombreNoValidoException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.general.PrecioNoValidoException;
import com.jeffersonvilla.emazon.stock.dominio.modelo.Articulo;
import com.jeffersonvilla.emazon.stock.dominio.modelo.Categoria;
import com.jeffersonvilla.emazon.stock.dominio.modelo.Marca;
import com.jeffersonvilla.emazon.stock.dominio.spi.IArticuloPersistenciaPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Set;

import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorArticulo.*;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorGenerales.*;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorMarca.MARCA_NULO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ArticuloCasoUsoTest {

    @Mock
    private IArticuloPersistenciaPort persistencia;

    @InjectMocks
    private ArticuloCasoUso articuloCasoUso;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearArticuloConExito() {
        Articulo articulo = new Articulo(1L, "Laptop", "Laptop gaming",
                5, BigDecimal.valueOf(1200.0), mock(Marca.class), Set.of(mock(Categoria.class)));
        when(persistencia.crearArticulo(articulo)).thenReturn(articulo);

        Articulo resultado = articuloCasoUso.crearArticulo(articulo);

        assertEquals(articulo, resultado);
        verify(persistencia, times(1)).crearArticulo(articulo);
    }

    @Test
    void testCrearArticuloConCategoriasMenorAlMinimoLanzaExcepcion() {
        Articulo articulo = new Articulo(1L, "Laptop", "Laptop gaming",
                5, BigDecimal.valueOf(1200.0), mock(Marca.class), Set.of());

        Exception exception = assertThrows(CreacionArticuloException.class, () -> {
            articuloCasoUso.crearArticulo(articulo);
        });

        assertEquals(CANTIDAD_MINIMA_CATEGORIAS, exception.getMessage());
        verify(persistencia, never()).crearArticulo(articulo);
    }

    @Test
    void testCrearArticuloConCategoriasMayorAlMaximoLanzaExcepcion() {
        Articulo articulo = new Articulo(1L, "Laptop", "Laptop gaming",
                5, BigDecimal.valueOf(1200.0), mock(Marca.class),
                Set.of( mock(Categoria.class),
                        mock(Categoria.class),
                        mock(Categoria.class),
                        mock(Categoria.class)));

        Exception exception = assertThrows(CreacionArticuloException.class, () -> {
            articuloCasoUso.crearArticulo(articulo);
        });

        assertEquals(CANTIDAD_MAXIMA_CATEGORIAS, exception.getMessage());
        verify(persistencia, never()).crearArticulo(articulo);
    }

    @Test
    void testCrearArticuloConMarcaNulaLanzaExcepcion() {
        Articulo articulo = new Articulo(1L, "Laptop", "Laptop gaming",
                5, BigDecimal.valueOf(1200.0), null, Set.of(mock(Categoria.class)));

        Exception exception = assertThrows(CreacionArticuloException.class, () -> {
            articuloCasoUso.crearArticulo(articulo);
        });

        assertEquals(MARCA_NULO, exception.getMessage());
        verify(persistencia, never()).crearArticulo(articulo);
    }

    @Test
    void testCrearArticuloConNombreNuloLanzaExcepcion() {
        Articulo articulo = new Articulo(1L, null, "Laptop gaming",
                5, BigDecimal.valueOf(1200.0), mock(Marca.class), Set.of(mock(Categoria.class)));

        Exception exception = assertThrows(NombreNoValidoException.class, () -> {
            articuloCasoUso.crearArticulo(articulo);
        });

        assertEquals(NOMBRE_NULL, exception.getMessage());
        verify(persistencia, never()).crearArticulo(articulo);
    }

    @Test
    void testCrearArticuloConDescripcionNulaLanzaExcepcion() {
        Articulo articulo = new Articulo(1L, "Laptop", null,
                5, BigDecimal.valueOf(1200.0), mock(Marca.class), Set.of(mock(Categoria.class)));

        Exception exception = assertThrows(DescripcionNoValidaException.class, () -> {
            articuloCasoUso.crearArticulo(articulo);
        });

        assertEquals(DESCRIPCION_NULL, exception.getMessage());
        verify(persistencia, never()).crearArticulo(articulo);
    }

    @Test
    void testCrearArticuloCantidadNulaLanzaExcepcion() {
        Articulo articulo = new Articulo(1L, "Laptop", "Laptop gaming",
                null, BigDecimal.valueOf(1200.0), mock(Marca.class), Set.of(mock(Categoria.class)));

        Exception exception = assertThrows(CantidadNoValidaException.class, () -> {
            articuloCasoUso.crearArticulo(articulo);
        });

        assertEquals(CANTIDAD_NULL, exception.getMessage());
        verify(persistencia, never()).crearArticulo(articulo);
    }

    @Test
    void testCrearArticuloCantidadNegativaLanzaExcepcion() {
        Articulo articulo = new Articulo(1L, "Laptop", "Laptop gaming",
                -1, BigDecimal.valueOf(1200.0), mock(Marca.class), Set.of(mock(Categoria.class)));

        Exception exception = assertThrows(CantidadNoValidaException.class, () -> {
            articuloCasoUso.crearArticulo(articulo);
        });

        assertEquals(CANTIDAD_NEGATIVA, exception.getMessage());
        verify(persistencia, never()).crearArticulo(articulo);
    }

    @Test
    void testCrearArticuloPrecioNuloLanzaExcepcion() {
        Articulo articulo = new Articulo(1L, "Laptop", "Laptop gaming",
                5, null, mock(Marca.class), Set.of(mock(Categoria.class)));

        Exception exception = assertThrows(PrecioNoValidoException.class, () -> {
            articuloCasoUso.crearArticulo(articulo);
        });

        assertEquals(PRECIO_NULL, exception.getMessage());
        verify(persistencia, never()).crearArticulo(articulo);
    }

    @Test
    void testCrearArticuloPrecioNegativoLanzaExcepcion() {
        Articulo articulo = new Articulo(1L, "Laptop", "Laptop gaming",
                5, BigDecimal.valueOf(-3456.0), mock(Marca.class), Set.of(mock(Categoria.class)));

        Exception exception = assertThrows(PrecioNoValidoException.class, () -> {
            articuloCasoUso.crearArticulo(articulo);
        });

        assertEquals(PRECIO_NEGATIVO, exception.getMessage());
        verify(persistencia, never()).crearArticulo(articulo);
    }
}