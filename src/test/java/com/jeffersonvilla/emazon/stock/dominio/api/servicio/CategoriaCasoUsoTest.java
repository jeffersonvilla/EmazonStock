package com.jeffersonvilla.emazon.stock.dominio.api.servicio;

import com.jeffersonvilla.emazon.stock.dominio.excepciones.categoria.CategoriaNoExisteException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.categoria.CreacionCategoriaException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.categoria.ListarCategoriaException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.general.IdNuloException;
import com.jeffersonvilla.emazon.stock.dominio.modelo.Categoria;
import com.jeffersonvilla.emazon.stock.dominio.spi.ICategoriaPersistenciaPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.ORDEN_ASCENDENTE;
import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.ORDEN_DESCENDENTE;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorCategoria.CATEGORIA_POR_ID_NO_EXISTE;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorCategoria.DESCRIPCION_TAMANO_MAXIMO;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorCategoria.ID_NULO_CATEGORIA;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorCategoria.NOMBRE_TAMANO_MAXIMO;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorGenerales.NOMBRE_NO_DISPONIBLE;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorGenerales.ORDENES_VALIDOS;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorGenerales.PAGINA_VALOR_MINIMO;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorGenerales.TAMANO_VALOR_MINIMO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CategoriaCasoUsoTest {

    @Mock
    private ICategoriaPersistenciaPort persistencia;

    @InjectMocks
    private CategoriaCasoUso categoriaCasoUso;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearCategoriaConExito() {
        Categoria categoria = new Categoria(1L, "Electrónica",
                "Categoría de dispositivos electrónicos");
        when(persistencia.obtenerCategoriaPorNombre("Electrónica")).thenReturn(Optional.empty());
        when(persistencia.crearCategoria(categoria)).thenReturn(categoria);

        Categoria resultado = categoriaCasoUso.crearCategoria(categoria);

        assertEquals(categoria, resultado);
        verify(persistencia, times(1)).crearCategoria(categoria);
    }

    @Test
    void testCrearCategoriaNombreNoDisponibleLanzaExcepcion() {
        Categoria categoria = new Categoria(1L, "Electrónica",
                "Categoría de dispositivos electrónicos");
        when(persistencia.obtenerCategoriaPorNombre("Electrónica")).thenReturn(Optional.of(categoria));

        Exception exception = assertThrows(CreacionCategoriaException.class, () -> {
            categoriaCasoUso.crearCategoria(categoria);
        });

        assertEquals(NOMBRE_NO_DISPONIBLE, exception.getMessage());
        verify(persistencia, never()).crearCategoria(categoria);
    }

    @Test
    void testCrearCategoriaNombreExcedeTamanoMaximoLanzaExcepcion() {
        String nombreLargo = "NombreMuyLargoQueExcedeElLimiteDeCincuentaCaracteresPermitidos";
        Categoria categoria = new Categoria(1L, nombreLargo, "Descripción válida");

        Exception exception = assertThrows(CreacionCategoriaException.class, () -> {
            categoriaCasoUso.crearCategoria(categoria);
        });

        assertEquals(NOMBRE_TAMANO_MAXIMO, exception.getMessage());
        verify(persistencia, never()).crearCategoria(categoria);
    }

    @Test
    void testCrearCategoriaDescripcionExcedeTamanoMaximoLanzaExcepcion() {
        String descripcionLarga = "DescripciónMuyLargaQueExcedeElLimiteDeNoventaCaracteresPermitidosDescripciónMuyLargaQueExcedeElLimiteDeNoventaCaracteresPermitidos";
        Categoria categoria = new Categoria(1L, "Electrónica", descripcionLarga);

        Exception exception = assertThrows(CreacionCategoriaException.class, () -> {
            categoriaCasoUso.crearCategoria(categoria);
        });

        assertEquals(DESCRIPCION_TAMANO_MAXIMO, exception.getMessage());
        verify(persistencia, never()).crearCategoria(categoria);
    }

    @Test
    void testListarCategoriaConParametrosValidosOrdenAscendente() {
        List<Categoria> categorias = Arrays.asList(
                new Categoria(1L, "Electrónica", "Categoría de dispositivos electrónicos"),
                new Categoria(2L, "Hogar", "Categoría de productos para el hogar")
        );
        when(persistencia.listarCategoriasPorNombre(0, 10, ORDEN_ASCENDENTE))
                .thenReturn(categorias);

        List<Categoria> resultado = categoriaCasoUso
                .listarCategoria(0, 10, ORDEN_ASCENDENTE);

        assertEquals(categorias, resultado);
        verify(persistencia, times(1))
                .listarCategoriasPorNombre(0, 10, ORDEN_ASCENDENTE);
    }

    @Test
    void testListarCategoriaConParametrosValidosOrdenDescendente() {
        List<Categoria> categorias = Arrays.asList(
                new Categoria(1L, "Electrónica", "Categoría de dispositivos electrónicos"),
                new Categoria(2L, "Hogar", "Categoría de productos para el hogar")
        );
        when(persistencia.listarCategoriasPorNombre(0, 10, ORDEN_DESCENDENTE))
                .thenReturn(categorias);

        List<Categoria> resultado = categoriaCasoUso
                .listarCategoria(0, 10, ORDEN_DESCENDENTE);

        assertEquals(categorias, resultado);
        verify(persistencia, times(1))
                .listarCategoriasPorNombre(0, 10, ORDEN_DESCENDENTE);
    }

    @Test
    void testListarCategoriaPaginaMenorQueMinimoLanzaExcepcion() {
        Exception exception = assertThrows(ListarCategoriaException.class, () -> {
            categoriaCasoUso.listarCategoria(-1, 10, ORDEN_ASCENDENTE);
        });

        assertEquals(PAGINA_VALOR_MINIMO, exception.getMessage());
        verify(persistencia, never()).listarCategoriasPorNombre(anyInt(), anyInt(), anyString());
    }

    @Test
    void testListarCategoriaTamanoMenorQueMinimoLanzaExcepcion() {
        Exception exception = assertThrows(ListarCategoriaException.class, () -> {
            categoriaCasoUso.listarCategoria(0, 0, ORDEN_ASCENDENTE);
        });

        assertEquals(TAMANO_VALOR_MINIMO, exception.getMessage());
        verify(persistencia, never()).listarCategoriasPorNombre(anyInt(), anyInt(), anyString());
    }

    @Test
    void testListarCategoriaConOrdenNoValidoLanzaExcepcion() {
        Exception exception = assertThrows(ListarCategoriaException.class, () -> {
            categoriaCasoUso.listarCategoria(0, 10, "INVALIDO");
        });

        assertEquals(ORDENES_VALIDOS, exception.getMessage());
        verify(persistencia, never()).listarCategoriasPorNombre(anyInt(), anyInt(), anyString());
    }

    @Test
    void obtenerCategoriaPorIdConIdNuloDebeLanzarIdNuloException() {
        Long id = null;

        IdNuloException exception = assertThrows(IdNuloException.class, () -> {
            categoriaCasoUso.obtenerCategoriaPorId(id);
        });

        assertEquals(ID_NULO_CATEGORIA, exception.getMessage());
        verify(persistencia, never()).obtenerCategoriaPorId(anyLong());
    }

    @Test
    void obtenerCategoriaPorIdCuandoCategoriaNoExisteDebeLanzarCategoriaNoExisteException() {
        Long id = 1L;
        when(persistencia.obtenerCategoriaPorId(id)).thenReturn(Optional.empty());

        CategoriaNoExisteException exception = assertThrows(CategoriaNoExisteException.class, () -> {
            categoriaCasoUso.obtenerCategoriaPorId(id);
        });

        assertEquals(CATEGORIA_POR_ID_NO_EXISTE+ " id: " + id, exception.getMessage());
        verify(persistencia, times(1)).obtenerCategoriaPorId(id);
    }

    @Test
    void obtenerCategoriaPorIdCuandoCategoriaExisteDebeRetornarCategoria() {
        Long id = 1L;
        Categoria categoriaEsperada = mock(Categoria.class);
        when(persistencia.obtenerCategoriaPorId(id)).thenReturn(Optional.of(categoriaEsperada));

        Categoria categoriaResultado = categoriaCasoUso.obtenerCategoriaPorId(id);

        assertNotNull(categoriaResultado);
        assertEquals(categoriaEsperada, categoriaResultado);
        verify(persistencia, times(1)).obtenerCategoriaPorId(id);
    }

}