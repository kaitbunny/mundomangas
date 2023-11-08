package net.mundomangas.backend.domain.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import net.mundomangas.backend.domain.model.Usuario;

@Service
public class TokenService {
	@Value("${api.security.token.secret}")
	private String secret;
	
	public String generateToken(Usuario usuario) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			String token = JWT.create()
					.withIssuer("mundomangas-backend-api")
					.withSubject(usuario.getEmail())
					.withExpiresAt(genExpirationDate())
					.sign(algorithm);
			
			return token;
		}
		catch(JWTCreationException ex) {
			throw new RuntimeException("Erro durante a geracao do token ", ex);
		}
	}
	
	public String validateToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			return JWT.require(algorithm)
					.withIssuer("mundomangas-backend-api")
					.build()
					.verify(token)
					.getSubject();
		}
		catch(JWTVerificationException ex) {
			return "";
		}
	}
	
	private Instant genExpirationDate() {
		return LocalDateTime.now().plusHours(168).toInstant(ZoneOffset.of("-03:00"));
	}
}
