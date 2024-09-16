package com.jeffersonvilla.emazon.stock.dominio.api.servicio;

import com.jeffersonvilla.emazon.stock.dominio.excepciones.NoEncontradoException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.articulo.ActualizacionArticuloException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.articulo.CreacionArticuloException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.articulo.ListarArticuloException;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.LISTAR_POR_ARTICULO;
import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.ORDEN_ASCENDENTE;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorArticulo.ARTICULO_NO_ENCONTRADO;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorArticulo.CANTIDAD_MAXIMA_CATEGORIAS;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorArticulo.CANTIDAD_MINIMA_CATEGORIAS;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorArticulo.CANTIDAD_NEGATIVA;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorArticulo.ERROR_AUMENTANDO_STOCK;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorArticulo.PRECIO_NEGATIVO;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorGenerales.ATRIBUTOS_PARA_LISTAR;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorGenerales.CANTIDAD_NULL;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorGenerales.DESCRIPCION_NULL;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorGenerales.NOMBRE_NULL;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorGenerales.ORDENES_VALIDOS;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorGenerales.PAGINA_VALOR_MINIMO;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorGenerales.PRECIO_NULL;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorGenerales.TAMANO_VALOR_MINIMO;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorMarca.MARCA_NULO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        Articulo articulo = new Articulo.ArticuloBuilder()
                .setId(1L)
                .setNombre("Laptop")
                .setDescripcion("Laptop gaming")
                .setCantidad(5)
                .setPrecio(BigDecimal.valueOf(1200.0))
                .setMarca(mock(Marca.class))
                .setCategorias(Set.of(mock(Categoria.class)))
                .build();
        when(persistencia.crearArticulo(articulo)).thenReturn(articulo);

        Articulo resultado = articuloCasoUso.crearArticulo(articulo);

        assertEquals(articulo, resultado);
        verify(persistencia, times(1)).crearArticulo(articulo);
    }

    @Test
    void testCrearArticuloConCategoriasMenorAlMinimoLanzaExcepcion() {
        Articulo articulo = new Articulo.ArticuloBuilder()
                .setId(1L)
                .setNombre("Laptop")
                .setDescripcion("Laptop gaming")
                .setCantidad(5)
                .setPrecio(BigDecimal.valueOf(1200.0))
                .setMarca(mock(Marca.class))
                .build();

        Exception exception = assertThrows(CreacionArticuloException.class, () -> {
            articuloCasoUso.crearArticulo(articulo);
        });

        assertEquals(CANTIDAD_MINIMA_CATEGORIAS, exception.getMessage());
        verify(persistencia, never()).crearArticulo(articulo);
    }

    @Test
    void testCrearArticuloConCategoriasMayorAlMaximoLanzaExcepcion() {
        Articulo articulo = new Articulo.ArticuloBuilder()
                .setId(1L)
                .setNombre("Laptop")
                .setDescripcion("Laptop gaming")
                .setCantidad(5)
                .setPrecio(BigDecimal.valueOf(1200.0))
                .setMarca(mock(Marca.class))
                .setCategorias(
                    Set.of(
                        mock(Categoria.class),
                        mock(Categoria.class),
                        mock(Categoria.class),
                        mock(Categoria.class)))
                .build();

        Exception exception = assertThrows(CreacionArticuloException.class, () -> {
            articuloCasoUso.crearArticulo(articulo);
        });

        assertEquals(CANTIDAD_MAXIMA_CATEGORIAS, exception.getMessage());
        verify(persistencia, never()).crearArticulo(articulo);
    }

    @Test
    void testCrearArticuloConMarcaNulaLanzaExcepcion() {
        Articulo articulo = new Articulo.ArticuloBuilder()
                .setId(1L)
                .setNombre("Laptop")
                .setDescripcion("Laptop gaming")
                .setCantidad(5)
                .setPrecio(BigDecimal.valueOf(1200.0))
                .setCategorias(Set.of(mock(Categoria.class)))
                .build();

        Exception exception = assertThrows(CreacionArticuloException.class, () -> {
            articuloCasoUso.crearArticulo(articulo);
        });

        assertEquals(MARCA_NULO, exception.getMessage());
        verify(persistencia, never()).crearArticulo(articulo);
    }

    @Test
    void testCrearArticuloConNombreNuloLanzaExcepcion() {
        Articulo articulo = new Articulo.ArticuloBuilder()
                .setId(1L)
                .setDescripcion("Laptop gaming")
                .setCantidad(5)
                .setPrecio(BigDecimal.valueOf(1200.0))
                .setMarca(mock(Marca.class))
                .setCategorias(Set.of(mock(Categoria.class)))
                .build();

        Exception exception = assertThrows(NombreNoValidoException.class, () -> {
            articuloCasoUso.crearArticulo(articulo);
        });

        assertEquals(NOMBRE_NULL, exception.getMessage());
        verify(persistencia, never()).crearArticulo(articulo);
    }

    @Test
    void testCrearArticuloConDescripcionNulaLanzaExcepcion() {
        Articulo articulo = new Articulo.ArticuloBuilder()
                .setId(1L)
                .setNombre("Laptop")
                .setCantidad(5)
                .setPrecio(BigDecimal.valueOf(1200.0))
                .setMarca(mock(Marca.class))
                .setCategorias(Set.of(mock(Categoria.class)))
                .build();

        Exception exception = assertThrows(DescripcionNoValidaException.class, () -> {
            articuloCasoUso.crearArticulo(articulo);
        });

        assertEquals(DESCRIPCION_NULL, exception.getMessage());
        verify(persistencia, never()).crearArticulo(articulo);
    }

    @Test
    void testCrearArticuloCantidadNulaLanzaExcepcion() {
        Articulo articulo = new Articulo.ArticuloBuilder()
                .setId(1L)
                .setNombre("Laptop")
                .setDescripcion("Laptop gaming")
                .setPrecio(BigDecimal.valueOf(1200.0))
                .setMarca(mock(Marca.class))
                .setCategorias(Set.of(mock(Categoria.class)))
                .build();

        Exception exception = assertThrows(CantidadNoValidaException.class, () -> {
            articuloCasoUso.crearArticulo(articulo);
        });

        assertEquals(CANTIDAD_NULL, exception.getMessage());
        verify(persistencia, never()).crearArticulo(articulo);
    }

    @Test
    void testCrearArticuloCantidadNegativaLanzaExcepcion() {
        Articulo articulo = new Articulo.ArticuloBuilder()
                .setId(1L)
                .setNombre("Laptop")
                .setDescripcion("Laptop gaming")
                .setCantidad(-5)
                .setPrecio(BigDecimal.valueOf(1200.0))
                .setMarca(mock(Marca.class))
                .setCategorias(Set.of(mock(Categoria.class)))
                .build();

        Exception exception = assertThrows(CantidadNoValidaException.class, () -> {
            articuloCasoUso.crearArticulo(articulo);
        });

        assertEquals(CANTIDAD_NEGATIVA, exception.getMessage());
        verify(persistencia, never()).crearArticulo(articulo);
    }

    @Test
    void testCrearArticuloPrecioNuloLanzaExcepcion() {
        Articulo articulo = new Articulo.ArticuloBuilder()
                .setId(1L)
                .setNombre("Laptop")
                .setDescripcion("Laptop gaming")
                .setCantidad(5)
                .setMarca(mock(Marca.class))
                .setCategorias(Set.of(mock(Categoria.class)))
                .build();

        Exception exception = assertThrows(PrecioNoValidoException.class, () -> {
            articuloCasoUso.crearArticulo(articulo);
        });

        assertEquals(PRECIO_NULL, exception.getMessage());
        verify(persistencia, never()).crearArticulo(articulo);
    }

    @Test
    void testCrearArticuloPrecioNegativoLanzaExcepcion() {
        Articulo articulo = new Articulo.ArticuloBuilder()
                .setId(1L)
                .setNombre("Laptop")
                .setDescripcion("Laptop gaming")
                .setCantidad(5)
                .setPrecio(BigDecimal.valueOf(-3500.0))
                .setMarca(mock(Marca.class))
                .setCategorias(Set.of(mock(Categoria.class)))
                .build();

        Exception exception = assertThrows(PrecioNoValidoException.class, () -> {
            articuloCasoUso.crearArticulo(articulo);
        });

        assertEquals(PRECIO_NEGATIVO, exception.getMessage());
        verify(persistencia, never()).crearArticulo(articulo);
    }

    @Test
    void testListarArticuloConParametrosValidos() {
        List<Articulo> articulos = Arrays.asList(
                mock(Articulo.class),
                mock(Articulo.class)
        );
        when(persistencia.listarArticulos(0, 10,
                ORDEN_ASCENDENTE, LISTAR_POR_ARTICULO)).thenReturn(articulos);

        List<Articulo> resultado = articuloCasoUso
                .listarArticulo(0, 10, ORDEN_ASCENDENTE, LISTAR_POR_ARTICULO);

        assertEquals(articulos, resultado);
        verify(persistencia, times(1))
                .listarArticulos(0, 10, ORDEN_ASCENDENTE, LISTAR_POR_ARTICULO);
    }

    @Test
    void testListarArticuloPaginaMenorQueMinimoLanzaExcepcion() {
        Exception exception = assertThrows(ListarArticuloException.class, () -> {
            articuloCasoUso.listarArticulo(-1, 10, ORDEN_ASCENDENTE, LISTAR_POR_ARTICULO);
        });

        assertEquals(PAGINA_VALOR_MINIMO, exception.getMessage());
        verify(persistencia, never()).listarArticulos(anyInt(), anyInt(), anyString(), anyString());
    }

    @Test
    void testListarArticuloTamanoMenorQueMinimoLanzaExcepcion() {
        Exception exception = assertThrows(ListarArticuloException.class, () -> {
            articuloCasoUso.listarArticulo(0, 0, ORDEN_ASCENDENTE, LISTAR_POR_ARTICULO);
        });

        assertEquals(TAMANO_VALOR_MINIMO, exception.getMessage());
        verify(persistencia, never()).listarArticulos(anyInt(), anyInt(), anyString(), anyString());
    }

    @Test
    void testListarArticuloConOrdenNoValidoLanzaExcepcion() {
        Exception exception = assertThrows(ListarArticuloException.class, () -> {
            articuloCasoUso.listarArticulo(0, 10, "INVALIDO", LISTAR_POR_ARTICULO);
        });

        assertEquals(ORDENES_VALIDOS, exception.getMessage());
        verify(persistencia, never()).listarArticulos(anyInt(), anyInt(), anyString(), anyString());
    }

    @Test
    void testListarArticuloConAtributoParaListarNoValidoLanzaExcepcion() {
        Exception exception = assertThrows(ListarArticuloException.class, () -> {
            articuloCasoUso.listarArticulo(0, 10, ORDEN_ASCENDENTE, "INVALIDO");
        });

        assertEquals(ATRIBUTOS_PARA_LISTAR, exception.getMessage());
        verify(persistencia, never()).listarArticulos(anyInt(), anyInt(), anyString(), anyString());
    }

    @Test
    void testAumentarCantidadStockExitoso(){
        long idArticulo = 1L;
        int cantidadSuministro = 10;

        Articulo articulo = mock(Articulo.class);

        when(persistencia.obtenerArticuloPorId(idArticulo)).thenReturn(Optional.of(articulo));
        when(persistencia.actualizarCantidadStock(any(Articulo.class))).thenReturn(articulo);

        Articulo resultado = articuloCasoUso.aumentarCantidadStock(idArticulo, cantidadSuministro);

        assertNotNull(resultado);

        verify(persistencia).actualizarCantidadStock(any(Articulo.class));
        verify(persistencia).obtenerArticuloPorId(idArticulo);
    }

    @Test
    void testAumentarCantidadStockArticuloNoEncontrado(){
        long idArticulo = 1L;
        int cantidadSuministro = 10;

        when(persistencia.obtenerArticuloPorId(idArticulo)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NoEncontradoException.class,
                () -> articuloCasoUso.aumentarCantidadStock(idArticulo, cantidadSuministro));

        assertEquals(ARTICULO_NO_ENCONTRADO, exception.getMessage());

        verify(persistencia, never()).actualizarCantidadStock(any(Articulo.class));
        verify(persistencia).obtenerArticuloPorId(idArticulo);
    }

    @Test
    void testAumentarCantidadStockErrorActualizandoCantidad(){
        long idArticulo = 1L;
        int cantidadSuministro = 10;

        Articulo articulo = mock(Articulo.class);

        when(persistencia.obtenerArticuloPorId(idArticulo)).thenReturn(Optional.of(articulo));
        when(persistencia.actualizarCantidadStock(any(Articulo.class))).thenThrow(new RuntimeException());

        Exception exception = assertThrows(ActualizacionArticuloException.class,
                () -> articuloCasoUso.aumentarCantidadStock(idArticulo, cantidadSuministro));

        assertEquals(ERROR_AUMENTANDO_STOCK, exception.getMessage());

        verify(persistencia, times(3)).actualizarCantidadStock(any(Articulo.class));
        verify(persistencia, times(3)).obtenerArticuloPorId(idArticulo);
    }
}