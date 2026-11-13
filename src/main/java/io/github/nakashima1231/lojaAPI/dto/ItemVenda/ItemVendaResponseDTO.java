package io.github.nakashima1231.lojaAPI.dto.ItemVenda;

import io.github.nakashima1231.lojaAPI.entities.ItemVenda;

import java.math.BigDecimal;

public record ItemVendaResponseDTO(
         Long produtoId,
         String nomeProduto,
         Integer quantidade,
         BigDecimal precoUnitario
) {
    public ItemVendaResponseDTO(ItemVenda i) {
        this(
                i.getProduto().getId(),
                i.getProduto().getNome(),
                i.getQuantidade(),
                i.getPrecoUnitario()
        );
    }
}
