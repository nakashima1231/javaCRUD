package io.github.nakashima1231.lojaAPI.dto.ProdutoDTO;

import io.github.nakashima1231.lojaAPI.entities.Produto;

import java.math.BigDecimal;

public record ProdutoResponseDTO(
         Long id,
         String nome,
         String descricao,
         BigDecimal preco,
         int quantidadeEstoque,
         boolean ativo
) {
    public ProdutoResponseDTO(Produto p) {
        this(p.getId(), p.getNome(), p.getDescricao(), p.getPreco(), p.getQuantidadeEstoque(), p.isAtivo());
    }
}
