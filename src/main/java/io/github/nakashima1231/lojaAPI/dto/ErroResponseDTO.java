package io.github.nakashima1231.lojaAPI.dto;

import java.time.LocalDateTime;

public record ErroResponseDTO(
        int status,
        String mensagem,
        LocalDateTime timestamp
) {}
