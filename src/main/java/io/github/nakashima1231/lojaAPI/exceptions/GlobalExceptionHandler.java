package io.github.nakashima1231.lojaAPI.exceptions;

import io.github.nakashima1231.lojaAPI.dto.ErroResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErroResponseDTO> tratarNaoEncontrado(RecursoNaoEncontradoException ex) {
        return montarErro(HttpStatus.NOT_FOUND, ex.getMessage());
    }


    @ExceptionHandler(ConflitoException.class)
    public ResponseEntity<ErroResponseDTO> tratarConfito(ConflitoException ex) {
        return montarErro(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<ErroResponseDTO> tratarRegraDeNegocio(RegraNegocioException ex) {
        return montarErro(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());
    }

    private ResponseEntity<ErroResponseDTO> montarErro(HttpStatus status, String mensagem) {
        ErroResponseDTO erro = new ErroResponseDTO(status.value(), mensagem, LocalDateTime.now());
        return ResponseEntity.status(status).body(erro);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResponseDTO> tratarErroInesperado(Exception ex) {
        log.error("Erro inesperado", ex);
        return montarErro(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno no servidor");
    }

}