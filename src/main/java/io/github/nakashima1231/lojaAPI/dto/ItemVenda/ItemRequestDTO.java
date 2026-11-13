package io.github.nakashima1231.lojaAPI.dto.ItemVenda;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ItemRequestDTO(
        @NotNull
        Long produtoId,

        @NotNull
        @Positive
        Integer quantidade
) {
}
