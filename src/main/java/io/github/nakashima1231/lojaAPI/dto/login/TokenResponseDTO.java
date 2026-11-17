package io.github.nakashima1231.lojaAPI.dto.login;

public record TokenResponseDTO(
        String token
) {
    public TokenResponseDTO() {
        this("login-ok");
    }
}
