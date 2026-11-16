package io.github.nakashima1231.lojaAPI.config;

import io.github.nakashima1231.lojaAPI.entities.Usuario;
import io.github.nakashima1231.lojaAPI.repository.UsuarioRepository;
import io.github.nakashima1231.lojaAPI.util.Perfil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminInicial implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.email}")
    private String email;

    @Value("${app.admin.senha}")
    private String senha;

    @Override
    public void run(String... args) {
        if(usuarioRepository.existsByEmail(email)) {
            return;
        }

        Usuario admin = new Usuario();
        admin.setEmail(email);
        admin.setSenha(passwordEncoder.encode(senha));
        admin.setPerfil(Perfil.ADMIN);

        usuarioRepository.save(admin);
    }
}