package com.jeffersonvilla.emazon.stock.infraestructura.seguridad;

import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    @Mock
    private UserDetails userDetails;

    private final String tokenGenerado = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2wiOiJhZG1pbiIsInN1YiI6ImNvcnJlb0BwcnVlYmEuY29tIiwiZXhwIjoyODU5OTk0NzI1LCJpYXQiOjE1MTYyMzkwMjJ9.pNzRzvgjMYZaxY874YHXjTo8jCVxvw3VWxnsBb8tJ6A";

    private final String correo = "correo@prueba.com";
    private final String rol = "admin";

    @Test
    void testExtraerUsername() {
        String username = jwtService.extraerUsername(tokenGenerado);
        assertEquals(correo, username);
    }

    @Test
    void testExtraerRol() {
        assertEquals(rol, jwtService.extraerRol(tokenGenerado));
    }

    @Test
    void testTokenValido() {
        assertTrue(jwtService.tokenValido(tokenGenerado));
    }

    @Test
    void testTokenInvalidoPorExpiracion() {

        String token = "eyJhbGciOiJIUzUxMiJ9.eyJpZF91c3VhcmlvIjoxLCJyb2wiOiJhdXhfYm9kZWd" +
                "hIiwic3ViIjoidXN1YXJpb0BlamVtcGxvLmNvbSIsImlhdCI6MTcyNTU0ODY1MCwiZXhwIj" +
                "oxNzE1NjI1MDUwfQ.d57RIrJv6gjXj310gzK5FXrA7UQNj8X4h1sYApTjgkECEG-dJJM9Nl" +
                "rQ4xW8Ntrhu4GvpbSkmV_JxDtiBXJ5Cg";

        assertThrows(ExpiredJwtException.class,
                () -> jwtService.tokenValido(token));
    }

    @Test
    void testExtraerExpiration() {
        Date expiration = jwtService.extractExpiration(tokenGenerado);
        assertNotNull(expiration);
        assertTrue(expiration.after(new Date()));
    }
}