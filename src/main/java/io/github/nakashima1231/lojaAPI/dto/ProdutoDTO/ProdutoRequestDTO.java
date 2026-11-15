package io.github.nakashima1231.lojaAPI.dto.ProdutoDTO;


import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProdutoRequestDTO(
        @NotBlank
        @Size(max = 100)
        String nome,

        @Size(max = 200)
        String descricao,

        @NotNull
        @DecimalMin(value = "0.0", inclusive = false)
        BigDecimal preco,

        @NotNull
        @PositiveOrZero
        Integer quantidadeEstoque
) {

}
