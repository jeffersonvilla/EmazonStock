package com.jeffersonvilla.emazon.stock.configuracion;

import com.jeffersonvilla.emazon.stock.infraestructura.seguridad.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.ROL_ADMIN;
import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.ROL_AUX_BODEGA;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SeguridadConfig {

    private final JwtAuthenticationFilter jwtFilter;

    private static final String RUTA_LISTAR_ARTICULO = "/articulo/listar";
    private static final String RUTA_LISTAR_CATEGORIA = "/categoria/listar";
    private static final String RUTA_LISTAR_MARCA = "/marca/listar";

    private static final String RUTA_CREAR_ARTICULO = "/articulo/crear";
    private static final String RUTA_CREAR_CATEGORIA = "/categoria/crear";
    private static final String RUTA_CREAR_MARCA = "/marca/crear";

    private static final String RUTA_AUMENTAR_STOCK = "/articulo/aumentar/stock";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(
                                RUTA_LISTAR_ARTICULO,
                                RUTA_LISTAR_CATEGORIA,
                                RUTA_LISTAR_MARCA
                        ).permitAll()
                        .requestMatchers(
                                RUTA_CREAR_ARTICULO,
                                RUTA_CREAR_CATEGORIA,
                                RUTA_CREAR_MARCA
                        ).hasAuthority(ROL_ADMIN)
                        .requestMatchers(RUTA_AUMENTAR_STOCK).hasAuthority(ROL_AUX_BODEGA)
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {

        return config.getAuthenticationManager();
    }

}
