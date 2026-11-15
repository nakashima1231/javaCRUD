package io.github.nakashima1231.lojaAPI.dto.ClienteDTO;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;


public record ClienteRequestDTO(
        @NotBlank
        @Size(max = 100)
        String nome,

        @NotBlank
        @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 números")
        @CPF
        String cpf,

        @NotBlank
        @Email
        String email,

        @Pattern(regexp = "\\d{10,11}", message = "Telefone deve ter DDD + número, só dígitos (10 ou 11)")
        String telefone
) {

}
