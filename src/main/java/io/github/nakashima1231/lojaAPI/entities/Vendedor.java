package io.github.nakashima1231.lojaAPI.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Vendedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String matricula;

    private BigDecimal percentualComissao;
    private boolean ativo;
}
