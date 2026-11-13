package io.github.nakashima1231.lojaAPI.dto.Vendedor;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record VendedorRequestDTO(
        @NotBlank
        @Size(max = 100)
        String nome,

        @NotBlank
        @Email
        String email,

        @NotBlank
        String matricula,

        @NotNull
        @DecimalMin(value = "0.0", inclusive = true)
        @DecimalMax(value = "100.0", inclusive = false)
        BigDecimal percentualComissao
) {

}
