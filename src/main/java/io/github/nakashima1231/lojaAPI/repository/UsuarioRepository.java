package io.github.nakashima1231.lojaAPI.repository;

import io.github.nakashima1231.lojaAPI.entities.Usuario;
import io.github.nakashima1231.lojaAPI.util.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByPerfil(Perfil perfil);
}
