package io.github.nakashima1231.lojaAPI.exceptions;

public class ClienteNotFoundException extends RuntimeException {
    public ClienteNotFoundException(Long id) {

        super("Nao existe um cliente com o seguinte id: " + id);
    }
}
