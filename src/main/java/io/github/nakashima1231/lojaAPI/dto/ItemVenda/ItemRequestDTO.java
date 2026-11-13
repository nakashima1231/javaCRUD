package io.github.nakashima1231.lojaAPI.dto.ItemVenda;


import jakarta.validation.constraints.Positive;

public record ItemRequestDTO(
        Long produtoId,

        @Positive
        Integer quantidade
) {
}
