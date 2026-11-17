package io.github.nakashima1231.lojaAPI.service;

import io.github.nakashima1231.lojaAPI.entities.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {

    public String gerarToken(Usuario usuario){
        SecretKey key = Keys.hmacShaKeyFor("${app.jwt.secret}".getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .subject(usuario.getEmail())
                .issuedAt(new Date())
                .claim("perfil", usuario.getPerfil())
                .expiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(key)
                .compact();
    }


}
