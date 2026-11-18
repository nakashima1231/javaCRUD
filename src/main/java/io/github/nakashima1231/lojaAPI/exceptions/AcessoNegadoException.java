package io.github.nakashima1231.lojaAPI.exceptions;

public class AcessoNegadoException extends RuntimeException {
    public AcessoNegadoException() {
        super("Você não tem permissão para esta operação");
    }
}
