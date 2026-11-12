package io.github.nakashima1231.lojaAPI.dto.Vendedor;

import io.github.nakashima1231.lojaAPI.entities.Vendedor;
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
        @DecimalMin(value = "0.0", inclusive = false)
        @DecimalMax(value = "100.0", inclusive = false)
        BigDecimal percentualComissao
) {
    public VendedorRequestDTO(Vendedor v) {
        this(v.getNome(), v.getEmail(), v.getMatricula(), v.getPercentualComissao());
    }
}
