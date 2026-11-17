package io.github.nakashima1231.lojaAPI.dto.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(
        @NotBlank
        @Email
        String email,

        @NotBlank
        String senha
) {
}
