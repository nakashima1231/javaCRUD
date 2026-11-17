package io.github.nakashima1231.lojaAPI.exceptions;

import io.github.nakashima1231.lojaAPI.dto.erro.CampoErroDTO;
import io.github.nakashima1231.lojaAPI.dto.erro.ErroResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tools.jackson.databind.exc.MismatchedInputException;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponseDTO> tratarValidacao(MethodArgumentNotValidException ex) {
        List<CampoErroDTO> erros = ex.getBindingResult().getFieldErrors().stream()
                /* transforme cada FieldError em CampoErroDTO */
                .map(fieldError -> new CampoErroDTO(fieldError.getField(), fieldError.getDefaultMessage()))
                .toList();

        ErroResponseDTO erro = new ErroResponseDTO(400, "Dados inválidos", LocalDateTime.now(), erros);
        return ResponseEntity.badRequest().body(erro);
    }

    private ResponseEntity<ErroResponseDTO> montarErro(HttpStatus status, String mensagem) {
        ErroResponseDTO erro = new ErroResponseDTO(status.value(), mensagem, LocalDateTime.now());
        return ResponseEntity.status(status).body(erro);
    }


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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResponseDTO> tratarErroInesperado(Exception ex) {
        log.error("Erro inesperado", ex);
        return montarErro(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno no servidor");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErroResponseDTO> tratarJsonInvalido(HttpMessageNotReadableException ex) {
        if (ex.getCause() instanceof MismatchedInputException mie && !mie.getPath().isEmpty()) {
            String campo = mie.getPath().getLast().getPropertyName();
            String tipo = mie.getTargetType() != null
                    ? mudarTipo(mie.getTargetType().getSimpleName())
                    : "desconhecido";


            return montarErro(HttpStatus.BAD_REQUEST,
                    "Campo '" + campo + "' com tipo inválido. Esperado: " + tipo);
        }
        return montarErro(HttpStatus.BAD_REQUEST, "JSON mal formatado");
    }

    @ExceptionHandler(CredenciaisInvalidasException.class)
    public ResponseEntity<ErroResponseDTO> credenciaisErradas(Exception ex) {
        return montarErro(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }





    private String mudarTipo(String padrao) {
        return switch (padrao) {
            case "BigDecimal", "Integer", "int", "Long", "long", "Double", "double" -> "número";
            case "String" -> "texto";
            case "Boolean", "boolean" -> "verdadeiro ou falso";
            default -> padrao;
        };
    }
}