package io.github.nakashima1231.lojaAPI.repository;

import io.github.nakashima1231.lojaAPI.entities.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendedorRepository extends JpaRepository<Vendedor, Long> {
    boolean existsByEmailAndIdNot(String email, Long id);
    boolean existsByMatriculaAndIdNot(String matricula, Long id);

    boolean existsByEmail(String email);
    boolean existsByMatricula(String matricula);

    List<Vendedor> findByAtivoTrue();
}
