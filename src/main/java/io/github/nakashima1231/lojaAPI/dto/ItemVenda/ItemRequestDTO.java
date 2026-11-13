package io.github.nakashima1231.lojaAPI.dto.ItemVenda;

import io.github.nakashima1231.lojaAPI.entities.Produto;
import io.github.nakashima1231.lojaAPI.entities.Venda;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record ItemRequestDTO(
        @NotNull
        Venda venda,

        @NotNull
        Produto produto,

        @NotNull
        @PositiveOrZero
        Integer quantidade,

        @NotNull
        @DecimalMin(value = "0.0", inclusive = false)
        BigDecimal precoUnitario
) {
}
