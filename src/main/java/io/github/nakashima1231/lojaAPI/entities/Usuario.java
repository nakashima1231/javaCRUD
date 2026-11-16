package io.github.nakashima1231.lojaAPI.entities;

import io.github.nakashima1231.lojaAPI.util.Perfil;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Perfil perfil;

    @OneToOne
    @JoinColumn(name = "id_vendedor")
    private Vendedor vendedor;
}
