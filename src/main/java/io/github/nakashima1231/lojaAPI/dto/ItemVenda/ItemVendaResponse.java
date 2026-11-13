package io.github.nakashima1231.lojaAPI.dto.ItemVenda;

import io.github.nakashima1231.lojaAPI.entities.ItemVenda;
import io.github.nakashima1231.lojaAPI.entities.Produto;
import io.github.nakashima1231.lojaAPI.entities.Venda;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;

public record ItemVendaResponse(
         Long id,
         Venda venda,
         Produto produto,
         int quantidade,
         BigDecimal precoUnitario
) {
    public ItemVendaResponse(ItemVenda i) {
        this(i.getId(), i.getVenda(), i.getProduto(), i.getQuantidade(), i.getPrecoUnitario());
    }
}
