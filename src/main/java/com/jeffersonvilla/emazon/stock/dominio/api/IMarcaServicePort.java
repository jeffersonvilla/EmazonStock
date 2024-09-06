package com.jeffersonvilla.emazon.stock.dominio.api;

import com.jeffersonvilla.emazon.stock.dominio.modelo.Marca;

import java.util.List;

public interface IMarcaServicePort {

    Marca crearMarca(Marca marca);

    List<Marca> listarMarca(int pagina, int tamano, String orden);

    Marca obtenerMarcaPorId(Long id);
}
