package io.github.nakashima1231.lojaAPI.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponseDTO> tratarValidacao(MethodArgumentNotValidException ex) {
        List<CampoErroDTO> erros = ex.getBindingResult().getFieldErrors().stream()
                /* transforme cada FieldError em CampoErroDTO */
                .map(fieldError -> new CampoErroDTO(fieldError.getField(), fieldError.getDefaultMessage()))
                .toList();

        ErroResponseDTO erro = new ErroResponseDTO(400, "Dados inválidos", LocalDateTime.now(), erros);
        return ResponseEntity.badRequest().body(erro);
    }
}
