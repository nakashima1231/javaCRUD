package io.github.nakashima1231.lojaAPI.dto.ClienteDTO;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;


public record ClienteRequestDTO(
        @NotBlank
        @Size(max = 100)
        String nome,

        @NotBlank
        @Size(min = 11, max = 11)
        @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 números")
        //@CPF
        String cpf,

        @NotBlank
        @Email
        String email,

        String telefone
) {

}
