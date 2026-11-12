package io.github.nakashima1231.lojaAPI.dto.ClienteDTO;

import io.github.nakashima1231.lojaAPI.entities.Cliente;

import java.time.LocalDateTime;

public record ClienteResponseDTO(
        Long id,
        String nome,
        String cpf,
        String email,
        String telefone,
        LocalDateTime dataCadastro
) {
    public ClienteResponseDTO(Cliente c) {
        this(c.getId(), c.getNome(), c.getCpf(), c.getEmail(), c.getTelefone(), c.getDataCadastro());
    }
}

