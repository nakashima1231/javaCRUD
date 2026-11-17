package io.github.nakashima1231.lojaAPI.dto.Venda;

import io.github.nakashima1231.lojaAPI.dto.ItemVenda.ItemRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record VendaRequestDTO(
        @NotNull
        Long clienteId,

        @NotEmpty
        @Valid
        List<ItemRequestDTO> itens
) {
}
