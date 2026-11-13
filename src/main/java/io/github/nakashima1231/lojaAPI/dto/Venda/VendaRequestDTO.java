package io.github.nakashima1231.lojaAPI.dto.Venda;

import io.github.nakashima1231.lojaAPI.entities.Cliente;
import io.github.nakashima1231.lojaAPI.entities.Produto;
import io.github.nakashima1231.lojaAPI.util.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record VendaRequestDTO(
        @NotNull
        Cliente cliente,

        @NotNull
        BigDecimal valorTotal,

        @NotEmpty
        List<Produto> itens
) {
}
