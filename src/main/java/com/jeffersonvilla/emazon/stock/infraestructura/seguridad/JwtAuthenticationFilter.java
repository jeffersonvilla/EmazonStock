package com.jeffersonvilla.emazon.stock.infraestructura.seguridad;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeffersonvilla.emazon.stock.infraestructura.rest.excepciones.RespuestaError;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

import static com.jeffersonvilla.emazon.stock.infraestructura.seguridad.Constantes.AUTHORIZATION;
import static com.jeffersonvilla.emazon.stock.infraestructura.seguridad.Constantes.BEARER;
import static com.jeffersonvilla.emazon.stock.infraestructura.seguridad.Constantes.JWT_TOKEN_EXPIRADO;
import static com.jeffersonvilla.emazon.stock.infraestructura.seguridad.Constantes.JWT_TOKEN_NO_VALIDO;
import static com.jeffersonvilla.emazon.stock.infraestructura.seguridad.Constantes.TAMANO_HEADER;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader(AUTHORIZATION);
        final String jwt;
        if (authHeader == null || !authHeader.startsWith(BEARER)) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(TAMANO_HEADER);

        try {

            String username = jwtService.extraerUsername(jwt);
            String rol = jwtService.extraerRol(jwt);

            if (
                    username != null && rol != null &&
                    SecurityContextHolder.getContext().getAuthentication() == null &&
                    jwtService.tokenValido(jwt)
            ) {

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                username, null,
                                List.of(new SimpleGrantedAuthority(rol)));

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

            filterChain.doFilter(request, response);

        } catch (ExpiredJwtException e) {
            enviarRespuestaError(response, HttpStatus.UNAUTHORIZED.value(), JWT_TOKEN_EXPIRADO);
        } catch (SignatureException | MalformedJwtException | IllegalArgumentException e) {
            enviarRespuestaError(response, HttpStatus.UNAUTHORIZED.value(), JWT_TOKEN_NO_VALIDO);
        }
    }

    private void enviarRespuestaError(
            HttpServletResponse response, int status, String message
    ) throws IOException {

        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON.toString());
        response.getWriter().write(new ObjectMapper().writeValueAsString(
                new RespuestaError(HttpStatus.UNAUTHORIZED.toString(), message)));
        response.getWriter().flush();
    }

}
