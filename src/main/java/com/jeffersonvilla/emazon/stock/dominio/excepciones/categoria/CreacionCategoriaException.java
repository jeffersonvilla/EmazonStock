package com.jeffersonvilla.emazon.stock.dominio.excepciones.categoria;

import com.jeffersonvilla.emazon.stock.dominio.excepciones.BadRequestException;

public class CreacionCategoriaException extends BadRequestException {
  public CreacionCategoriaException(String message) {
    super(message);
  }
}
