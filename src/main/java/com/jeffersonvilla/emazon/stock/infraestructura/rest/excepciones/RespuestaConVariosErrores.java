package com.jeffersonvilla.emazon.stock.infraestructura.rest.excepciones;

import java.util.Map;

public record RespuestaConVariosErrores (String status, Map<String, String> mensajes) {

}
