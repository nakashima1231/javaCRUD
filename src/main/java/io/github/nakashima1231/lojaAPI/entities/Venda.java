package io.github.nakashima1231.lojaAPI.entities;

import io.github.nakashima1231.lojaAPI.util.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private Cliente cliente;

    @OneToMany(mappedBy = "vendedor", cascade = CascadeType.ALL)
    private Vendedor vendedor;

    private LocalDateTime dataVenda;
    private BigDecimal valorTotal;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL)
    private List<ItemVenda> itens = new ArrayList<>();

}