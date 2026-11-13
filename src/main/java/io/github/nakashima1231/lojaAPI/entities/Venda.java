package io.github.nakashima1231.lojaAPI.entities;

import io.github.nakashima1231.lojaAPI.util.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Cliente cliente;
    private LocalDateTime dataVenda;
    private BigDecimal valorTotal;
    private List<Produto> itens;
    private Status status;
}