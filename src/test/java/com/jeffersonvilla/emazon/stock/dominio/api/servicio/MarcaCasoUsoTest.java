package com.jeffersonvilla.emazon.stock.dominio.api.servicio;

import com.jeffersonvilla.emazon.stock.dominio.excepciones.marca.MarcaNoExisteException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.general.IdNuloException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.marca.ListarMarcaException;
import com.jeffersonvilla.emazon.stock.dominio.excepciones.marca.CreacionMarcaException;
import com.jeffersonvilla.emazon.stock.dominio.modelo.Marca;
import com.jeffersonvilla.emazon.stock.dominio.spi.IMarcaPersistenciaPort;
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
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorGenerales.NOMBRE_NO_DISPONIBLE;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorGenerales.ORDENES_VALIDOS;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorGenerales.PAGINA_VALOR_MINIMO;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorGenerales.TAMANO_VALOR_MINIMO;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorMarca.MARCA_POR_ID_NO_EXISTE;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorMarca.ID_NULO_MARCA;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorMarca.DESCRIPCION_TAMANO_MAXIMO;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorMarca.NOMBRE_TAMANO_MAXIMO;
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

class MarcaCasoUsoTest {

    @Mock
    private IMarcaPersistenciaPort persistencia;

    @InjectMocks
    private MarcaCasoUso marcaCasoUso;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearMarcaConExito() {
        String nombreMarca = "Marca A";
        Marca marca = new Marca(1L, nombreMarca, "Descripción de la marca");
        when(persistencia.obtenerMarcaPorNombre(nombreMarca)).thenReturn(Optional.empty());
        when(persistencia.crearMarca(marca)).thenReturn(marca);

        Marca resultado = marcaCasoUso.crearMarca(marca);

        assertEquals(marca, resultado);
        verify(persistencia, times(1)).crearMarca(marca);
    }

    @Test
    void testCrearMarcaNombreNoDisponibleLanzaExcepcion() {
        String nombreMarca = "Marca A";
        Marca marca = new Marca(1L, nombreMarca, "Descripción de la marca");
        when(persistencia.obtenerMarcaPorNombre(nombreMarca)).thenReturn(Optional.of(marca));

        Exception exception = assertThrows(CreacionMarcaException.class, () -> {
            marcaCasoUso.crearMarca(marca);
        });

        assertEquals(NOMBRE_NO_DISPONIBLE, exception.getMessage());
        verify(persistencia, never()).crearMarca(marca);
    }

    @Test
    void testCrearMarcaNombreExcedeTamanoMaximoLanzaExcepcion() {
        String nombreLargo = "NombreMuyLargoQueExcedeElLimiteDeCincuentaCaracteresPermitidos";
        Marca marca = new Marca(1L, nombreLargo, "Descripción válida");

        Exception exception = assertThrows(CreacionMarcaException.class, () -> {
            marcaCasoUso.crearMarca(marca);
        });

        assertEquals(NOMBRE_TAMANO_MAXIMO, exception.getMessage());
        verify(persistencia, never()).crearMarca(marca);
    }

    @Test
    void testCrearMarcaDescripcionExcedeTamanoMaximoLanzaExcepcion() {
        String descripcionLarga = "DescripciónMuyLargaQueExcedeElLimiteDe120CaracteresPermitidosDescripciónMuyLargaQueExcedeElLimiteDe120CaracteresPermitidos";
        Marca marca = new Marca(1L, "Marca A", descripcionLarga);

        Exception exception = assertThrows(CreacionMarcaException.class, () -> {
            marcaCasoUso.crearMarca(marca);
        });

        assertEquals(DESCRIPCION_TAMANO_MAXIMO, exception.getMessage());
        verify(persistencia, never()).crearMarca(marca);
    }

    @Test
    void testListarMarcaConParametrosValidosOrdenAscendente() {
        List<Marca> marcas = Arrays.asList(
                new Marca(1L, "Marca A", "Marca de dispositivos electrónicos"),
                new Marca(2L, "Marca B", "Marca de productos para el hogar")
        );
        when(persistencia.listarMarcasPorNombre(0, 10, ORDEN_ASCENDENTE))
                .thenReturn(marcas);

        List<Marca> resultado = marcaCasoUso
                .listarMarca(0, 10, ORDEN_ASCENDENTE);

        assertEquals(marcas, resultado);
        verify(persistencia, times(1))
                .listarMarcasPorNombre(0, 10, ORDEN_ASCENDENTE);
    }

    @Test
    void testListarMarcaConParametrosValidosOrdenDescendente() {
        List<Marca> marcas = Arrays.asList(
                new Marca(1L, "Marca A", "Marca de dispositivos electrónicos"),
                new Marca(2L, "Marca B", "Marca de productos para el hogar")
        );
        when(persistencia.listarMarcasPorNombre(0, 10, ORDEN_DESCENDENTE))
                .thenReturn(marcas);

        List<Marca> resultado = marcaCasoUso
                .listarMarca(0, 10, ORDEN_DESCENDENTE);

        assertEquals(marcas, resultado);
        verify(persistencia, times(1))
                .listarMarcasPorNombre(0, 10, ORDEN_DESCENDENTE);
    }

    @Test
    void testListarMarcaPaginaMenorQueMinimoLanzaExcepcion() {
        Exception exception = assertThrows(ListarMarcaException.class, () -> {
            marcaCasoUso.listarMarca(-1, 10, ORDEN_ASCENDENTE);
        });

        assertEquals(PAGINA_VALOR_MINIMO, exception.getMessage());
        verify(persistencia, never()).listarMarcasPorNombre(anyInt(), anyInt(), anyString());
    }

    @Test
    void testListarMarcaTamanoMenorQueMinimoLanzaExcepcion() {
        Exception exception = assertThrows(ListarMarcaException.class, () -> {
            marcaCasoUso.listarMarca(0, 0, ORDEN_ASCENDENTE);
        });

        assertEquals(TAMANO_VALOR_MINIMO, exception.getMessage());
        verify(persistencia, never()).listarMarcasPorNombre(anyInt(), anyInt(), anyString());
    }

    @Test
    void testListarMarcaConOrdenNoValidoLanzaExcepcion() {
        Exception exception = assertThrows(ListarMarcaException.class, () -> {
            marcaCasoUso.listarMarca(0, 10, "INVALIDO");
        });

        assertEquals(ORDENES_VALIDOS, exception.getMessage());
        verify(persistencia, never()).listarMarcasPorNombre(anyInt(), anyInt(), anyString());
    }
    
    @Test
    void obtenerMarcaPorIdConIdNuloDebeLanzarIdNuloException() {
        Long id = null;

        IdNuloException exception = assertThrows(IdNuloException.class, () -> {
            marcaCasoUso.obtenerMarcaPorId(id);
        });

        assertEquals(ID_NULO_MARCA, exception.getMessage());
        verify(persistencia, never()).obtenerMarcaPorId(anyLong());
    }

    @Test
    void obtenerMarcaPorIdCuandoMarcaNoExisteDebeLanzarMarcaNoExisteException() {
        Long id = 1L;
        when(persistencia.obtenerMarcaPorId(id)).thenReturn(Optional.empty());

        MarcaNoExisteException exception = assertThrows(MarcaNoExisteException.class, () -> {
            marcaCasoUso.obtenerMarcaPorId(id);
        });

        assertEquals(MARCA_POR_ID_NO_EXISTE, exception.getMessage());
        verify(persistencia, times(1)).obtenerMarcaPorId(id);
    }

    @Test
    void obtenerMarcaPorIdCuandoMarcaExisteDebeRetornarMarca() {
        Long id = 1L;
        Marca marcaEsperada = mock(Marca.class);
        when(persistencia.obtenerMarcaPorId(id)).thenReturn(Optional.of(marcaEsperada));

        Marca marcaResultado = marcaCasoUso.obtenerMarcaPorId(id);

        assertNotNull(marcaResultado);
        assertEquals(marcaEsperada, marcaResultado);
        verify(persistencia, times(1)).obtenerMarcaPorId(id);
    }
}