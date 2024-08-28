package com.jeffersonvilla.emazon.stock.dominio.spi;

import com.jeffersonvilla.emazon.stock.dominio.modelo.Marca;

import java.util.List;
import java.util.Optional;

public interface IMarcaPersistenciaPort {

    public Marca crearMarca(Marca marca);
    public Optional<Marca> obtenerMarcaPorNombre(String nombre);
    public List<Marca> listarMarcasPorNombre(int pagina, int tamano, String orden);
}
