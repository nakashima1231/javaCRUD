package io.github.nakashima1231.lojaAPI.repository;

import io.github.nakashima1231.lojaAPI.entities.Cliente;
import io.github.nakashima1231.lojaAPI.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);

    boolean existsByCpfAndIdNot(String cpf, Long id);
    boolean existsByEmailAndIdNot(String email, Long id);

    List<Cliente> findByAtivoTrue();
    Optional<Cliente> findByIdAndAtivoTrue(Long id);
}
