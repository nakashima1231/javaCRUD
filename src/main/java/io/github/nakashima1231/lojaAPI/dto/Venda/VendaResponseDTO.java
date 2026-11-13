package io.github.nakashima1231.lojaAPI.dto.Venda;

import io.github.nakashima1231.lojaAPI.dto.ItemVenda.ItemVendaResponseDTO;
import io.github.nakashima1231.lojaAPI.entities.Venda;
import io.github.nakashima1231.lojaAPI.util.Status;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record VendaResponseDTO(
        Long id,
        Long clienteId,
        String nomeCliente,
        Long vendedorId,
        String nomeVendedor,
        LocalDateTime dataVenda,
        BigDecimal valorTotal,
        Status status,
        List<ItemVendaResponseDTO> itens
) {
    public VendaResponseDTO(Venda v) {
        this(
                v.getId(),
                v.getCliente().getId(),
                v.getCliente().getNome(),
                v.getVendedor().getId(),
                v.getVendedor().getNome(),
                v.getDataVenda(),
                v.getValorTotal(),
                v.getStatus(),
                v.getItens().stream().map(ItemVendaResponseDTO::new).toList()
        );
    }
}
