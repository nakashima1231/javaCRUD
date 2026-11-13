package io.github.nakashima1231.lojaAPI.dto.Venda;

import io.github.nakashima1231.lojaAPI.entities.Cliente;
import io.github.nakashima1231.lojaAPI.entities.Produto;
import io.github.nakashima1231.lojaAPI.entities.Venda;
import io.github.nakashima1231.lojaAPI.util.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record VendaResponseDTO(
        Long id,
        Cliente cliente,
        LocalDateTime dataVenda,
        BigDecimal valorTotal,
        List<Produto> itens,
        Status status
) {
    public VendaResponseDTO(Venda v) {
        this(v.getId(), v.getCliente(), v.getDataVenda(), v.getValorTotal(), v.getItens(), v.getStatus());
    }
}
