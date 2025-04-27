package com.organizo.organizobackend.util;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Gera e valida tokens JWT usando HS256.
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret:segredo123}")
    private String secret;

    @Value("${jwt.expiration-ms:3600000}")
    private long expirationMs;

    /** Gera um token para o subject (e-mail). */
    public String gerarToken(String subject) {
        Date agora = new Date();
        Date exp = new Date(agora.getTime() + expirationMs);
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(agora)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    /** Extrai o e-mail (subject) do token. */
    public String obterEmail(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
