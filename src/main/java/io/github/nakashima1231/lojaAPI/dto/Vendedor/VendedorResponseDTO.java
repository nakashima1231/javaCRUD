package io.github.nakashima1231.lojaAPI.dto.Vendedor;

import io.github.nakashima1231.lojaAPI.entities.Vendedor;

import java.math.BigDecimal;

public record VendedorResponseDTO(
        Long id,
        String nome,
        String email,
        String matricula,
        BigDecimal percentualComissao,
        boolean ativo
) {
    public VendedorResponseDTO(Vendedor v) {
        this(v.getId(), v.getNome(), v.getEmail(), v.getMatricula(), v.getPercentualComissao(), v.isAtivo());
    }
}

