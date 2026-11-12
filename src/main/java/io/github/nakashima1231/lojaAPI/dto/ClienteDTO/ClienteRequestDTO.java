package io.github.nakashima1231.lojaAPI.dto.ClienteDTO;

import io.github.nakashima1231.lojaAPI.entities.Cliente;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.UniqueElements;

public record ClienteRequestDTO(
        @NotBlank
        @Size(max = 100)
        String nome,

        @NotBlank
        @Size(min = 11, max = 11)
        @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 números")
        String cpf,

        @NotBlank
        @Email
        String email,

        String telefone
) {
    public ClienteRequestDTO(Cliente c) {
        this(c.getNome(), c.getCpf(), c.getEmail(), c.getTelefone());
    }
}
