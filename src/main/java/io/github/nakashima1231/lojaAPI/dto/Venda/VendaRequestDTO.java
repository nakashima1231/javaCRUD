package io.github.nakashima1231.lojaAPI.dto.Venda;

import io.github.nakashima1231.lojaAPI.dto.ItemVenda.ItemRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record VendaRequestDTO(
        Long clienteId,
        Long vendedorId,

        @NotEmpty
        @Valid
        List<ItemRequestDTO> itens
) {
}
