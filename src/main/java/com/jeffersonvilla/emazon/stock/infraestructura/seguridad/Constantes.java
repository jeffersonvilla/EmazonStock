package com.jeffersonvilla.emazon.stock.infraestructura.seguridad;

public class Constantes {

    private Constantes(){
        throw new IllegalStateException("Clase utilitaria");
    }

    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final Integer TAMANO_HEADER = 7;

    public static final String JWT_TOKEN_EXPIRADO = "El token jwt ha expirado";
    public static final String JWT_TOKEN_NO_VALIDO = "El token jwt no es válido";

    public static final String ROL_USUARIO_CLAIM = "rol";
}
