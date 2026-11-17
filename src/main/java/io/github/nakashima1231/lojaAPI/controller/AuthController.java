package io.github.nakashima1231.lojaAPI.controller;

import io.github.nakashima1231.lojaAPI.dto.login.LoginRequestDTO;
import io.github.nakashima1231.lojaAPI.dto.login.TokenResponseDTO;
import io.github.nakashima1231.lojaAPI.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
@RequiredArgsConstructor
public class AuthController {
    private final AuthService service;

    @PostMapping("/login")
    public TokenResponseDTO login(@RequestBody @Valid LoginRequestDTO dados) {
        return service.login(dados);
    }

}
