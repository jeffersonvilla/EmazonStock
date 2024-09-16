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


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/articulo/listar", "/categoria/listar", "/marca/listar")
                        .permitAll()
                        .requestMatchers(
                                "/articulo/crear", "/categoria/crear", "/marca/crear")
                        .hasAuthority(ROL_ADMIN)
                        .requestMatchers(
                                "/articulo/aumentar/stock")
                        .hasAuthority(ROL_AUX_BODEGA)
                        .anyRequest().permitAll()
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
