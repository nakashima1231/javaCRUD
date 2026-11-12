package io.github.nakashima1231.lojaAPI.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nome;
    @Column(nullable = false, unique = true)
    private String cpf;
    private String email;
    private String telefone;
    private LocalDateTime dataCadastro;
}
