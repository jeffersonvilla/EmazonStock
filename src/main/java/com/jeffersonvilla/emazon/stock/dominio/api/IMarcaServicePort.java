package com.jeffersonvilla.emazon.stock.dominio.api;

import com.jeffersonvilla.emazon.stock.dominio.modelo.Marca;

import java.util.List;

public interface IMarcaServicePort {

    public Marca crearMarca(Marca marca);

    public List<Marca> listarMarca(int pagina, int tamano, String orden);

    public Marca obtenerMarcaPorId(Long id);
}
