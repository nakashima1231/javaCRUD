package io.github.nakashima1231.lojaAPI.dto.erro;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErroResponseDTO(
        int status,
        String mensagem,
        LocalDateTime timestamp,
        List<CampoErroDTO> erros
) {
    // construtor "curto" para os erros sem campos
    public ErroResponseDTO(int status, String mensagem, LocalDateTime timestamp) {
        this(status, mensagem, timestamp, null);
    }


}
