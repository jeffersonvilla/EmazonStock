package com.jeffersonvilla.emazon.stock.dominio.api.servicio;

import com.jeffersonvilla.emazon.stock.dominio.excepciones.CreacionCategoriaException;
import com.jeffersonvilla.emazon.stock.dominio.modelo.Categoria;
import com.jeffersonvilla.emazon.stock.dominio.spi.ICategoriaPersistenciaPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorCategoria.DESCRIPCION_TAMANO_MAXIMO;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorCategoria.NOMBRE_TAMANO_MAXIMO;
import static com.jeffersonvilla.emazon.stock.dominio.util.MensajesErrorGenerales.NOMBRE_NO_DISPONIBLE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
}