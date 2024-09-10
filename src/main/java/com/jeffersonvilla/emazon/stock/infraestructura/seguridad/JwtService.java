package com.jeffersonvilla.emazon.stock.infraestructura.seguridad;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${CLAVE_JWT}")
    private String claveSecreta = "a522a5d03de1e7364f4985067bba67f4a0d6e4e1ed482d07bb70b" +
            "94a39f1580a8ce82094888ff449bc23a9bfc6715dbdcd82347c94127536825f468d3e4a2186" +
            "c20ca826705d642bcf486e0cbebf1a7df0b68d37772c8ddce876d500ee895492289380ed404" +
            "9ccfc0a7f8dfe700ddb52e56b6c9d658e32a1e2134fed35870dc83ba4b47c3336153e779bab" +
            "f72cab925002fec514c31e14fa09d3471a8bb178fd06baef291ad65f7058a09b4499ba30ae9" +
            "23002d6c9b469b440970486eac0b0523ade7421e465fce4012bca6bd2b5580c52f3212e0ec6" +
            "4bc0f5c5afca71bf1e8f6040fd83c7fdbec33e615d29c6a52d40e3615cf3aa55c014b3a1c15" +
            "50f26008c"; // clave para test unitarios

    private static final String ROL_USUARIO_CLAIM = "rol";

    public String extraerUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public boolean tokenValido(String token, UserDetails userDetails) {
        final String correoExtraido = extraerUsername(token);
        return (correoExtraido.equals(userDetails.getUsername()) && !tokenExpirado(token));
    }

    private Key obtenerClaveSecreta(){
        byte[] keyBytes = Decoders.BASE64.decode(claveSecreta);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) obtenerClaveSecreta())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean tokenValido(String token) {
        return !tokenExpirado(token);
    }

    private Boolean tokenExpirado(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String extraerRol(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get(ROL_USUARIO_CLAIM, String.class);
    }

}
