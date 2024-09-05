package com.example.musica2.security;

import com.example.musica2.model.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(Usuario user, Integer expiration) {
    try {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        String token = JWT.create()
                .withIssuer("Musica-api")
                .withClaim("usuarioId", user.getId())
                .withExpiresAt(this.generateExpirationDate(expiration))
                .sign(algorithm);

        return token;

    } catch (JWTCreationException exception) {
        throw new RuntimeException("Error ao gerar o token ", exception);
    }
}

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm)
                    .withIssuer("MusicaAPI")
                    .build()
                    .verify(token)
                    .getClaim("idFuncional").asString();

        } catch (JWTVerificationException exception) {
            return null;
        }
    }

    private Instant generateExpirationDate(Integer expiration) {
        return LocalDateTime.now().plusHours(expiration).toInstant(ZoneOffset.of("-03:00"));
    }

    public long getExpirationTime(String refreshTokenRequest) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            Date expirationDate = JWT.require(algorithm)
                    .withIssuer("Musica-api")
                    .build()
                    .verify(refreshTokenRequest)
                    .getExpiresAt();

            return expirationDate.getTime();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Error ao validar o token ", exception);
        }
    }

    public int getUsuarioId(String token) {
        // Remover o prefixo "Bearer " do token
        token = token.replace("Bearer ", "");

        // Decodificar o token
        DecodedJWT jwt = JWT.require(Algorithm.HMAC256(secret))
                .withIssuer("Musica-api")
                .build()
                .verify(token);

        int usuarioId = jwt.getClaim("usuarioId").asInt();
        return usuarioId;
    }
}