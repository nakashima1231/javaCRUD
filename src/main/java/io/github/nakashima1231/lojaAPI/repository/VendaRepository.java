package io.github.nakashima1231.lojaAPI.repository;

import io.github.nakashima1231.lojaAPI.entities.Cliente;
import io.github.nakashima1231.lojaAPI.entities.Venda;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VendaRepository extends JpaRepository<Venda, Long> {
    boolean findByCliente_IdAndCliente_AtivoTrue(Long id);

    boolean findByVendedor_IdAndVendedor_AtivoTrue(Long id);
}
