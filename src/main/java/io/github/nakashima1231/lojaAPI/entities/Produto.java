package io.github.nakashima1231.lojaAPI.entities;

import io.github.nakashima1231.lojaAPI.exceptions.RegraNegocioException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;
    private BigDecimal preco;
    private boolean ativo;
    private int quantidadeEstoque;

    public void baixarEstoque(int quantidade) {
        estoqueNegativo(quantidade);

        if (this.quantidadeEstoque < quantidade) {
            throw new RegraNegocioException("Estoque insuficiente para o produto: " + this.nome);
        }
        this.quantidadeEstoque -= quantidade;
    }

    public void reporEstoque(int quantidade) {
        estoqueNegativo(quantidade);
        this.quantidadeEstoque += quantidade;
    }

    private void estoqueNegativo(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser positiva");
        }
    }
}
