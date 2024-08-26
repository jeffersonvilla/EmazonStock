package com.jeffersonvilla.emazon.stock.dominio.api.servicio;

import com.jeffersonvilla.emazon.stock.dominio.excepciones.marca.CreacionMarcaException;
import com.jeffersonvilla.emazon.stock.dominio.modelo.Marca;
import com.jeffersonvilla.emazon.stock.dominio.spi.IMarcaPersistenciaPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorMarca.DESCRIPCION_TAMANO_MAXIMO;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorMarca.NOMBRE_TAMANO_MAXIMO;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorGenerales.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

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

}