package io.github.nakashima1231.lojaAPI.service;

import io.github.nakashima1231.lojaAPI.dto.login.LoginRequestDTO;
import io.github.nakashima1231.lojaAPI.dto.login.TokenResponseDTO;
import io.github.nakashima1231.lojaAPI.entities.Usuario;
import io.github.nakashima1231.lojaAPI.exceptions.CredenciaisInvalidasException;
import io.github.nakashima1231.lojaAPI.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public TokenResponseDTO login(LoginRequestDTO dados) {
        Usuario usuario = repository.findByEmail(dados.email())
                .orElseThrow(() -> new CredenciaisInvalidasException());

        if(!passwordEncoder.matches(dados.senha(), usuario.getSenha())) {
            throw new CredenciaisInvalidasException();
        }

        return new TokenResponseDTO(jwtService.gerarToken(usuario));
    }

}
