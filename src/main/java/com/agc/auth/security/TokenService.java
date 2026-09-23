package com.agc.auth.security;

import com.agc.auth.model.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;

    // O JWT possui três pedaços: Header, Payload e Signature.
    // Signature -> garante que ninguém alterou o token no meio do processo.
    //
    //
    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret); // Algorithm que encapsula HMAC-SHA256 e a chave secreta
            return JWT.create()
                    .withIssuer("api-auth")
                    .withSubject(user.getEmail())
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm); // Assina o token com algorithm
        } catch (JWTCreationException e) {
            throw new RuntimeException("Erro ao gerar token JWT", e);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm) // Exige que o token tenha sido assinado por algorithm
                    .withIssuer("api-auth") // Exige que o token tenha sido gerado pela API
                    .build()
                    .verify(token) // Verifica se a assinatura bate e se o token não está expirado
                    .getSubject();
        } catch (JWTVerificationException e) {
            return "";
        }
    }

    private Instant genExpirationDate() {
        // Fuso horário de Brasília.
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

}
