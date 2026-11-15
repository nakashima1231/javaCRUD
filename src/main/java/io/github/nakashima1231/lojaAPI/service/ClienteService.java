package io.github.nakashima1231.lojaAPI.service;

import io.github.nakashima1231.lojaAPI.dto.ClienteDTO.ClienteRequestDTO;
import io.github.nakashima1231.lojaAPI.dto.ClienteDTO.ClienteResponseDTO;
import io.github.nakashima1231.lojaAPI.entities.Cliente;
import io.github.nakashima1231.lojaAPI.exceptions.*;
import io.github.nakashima1231.lojaAPI.repository.ClienteRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository repository;

    //get privado
    private Cliente buscarEntidade(Long id) {
        Cliente cliente = repository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Nao existe um cliente com este id: " + id));
        return cliente;
    }


    public List<ClienteResponseDTO> listarTodos() {
        return repository.findByAtivoTrue().stream().map(ClienteResponseDTO:: new).toList();
    }

    public ClienteResponseDTO buscarPorId(Long id) {
        return repository.findByIdAndAtivoTrue(id).map(ClienteResponseDTO:: new).orElseThrow(() -> new RecursoNaoEncontradoException("Nao existe um cliente com este id: " + id));
    }

    //post
    @Transactional
    public ClienteResponseDTO criarCliente(ClienteRequestDTO dados) {
        if(repository.existsByCpf(dados.cpf())) {
            throw new ConflitoException("Ja existe um cliente com este cpf");
        }

        if(repository.existsByEmail(dados.email())) {
            throw new ConflitoException("Ja existe um cliente com este email");
        }

        Cliente novo = new Cliente();
        novo.setNome(dados.nome());
        novo.setCpf(dados.cpf());
        novo.setEmail(dados.email());
        novo.setTelefone(dados.telefone());
        novo.setDataCadastro(LocalDateTime.now());
        novo.setAtivo(true);

        repository.save(novo);

        return new ClienteResponseDTO(novo);
    }

    //put
    @Transactional
    public ClienteResponseDTO atualizar(ClienteRequestDTO dados, Long id) {
        Cliente cliente = buscarEntidade(id);

        if(repository.existsByEmailAndIdNot(dados.email(), id)) {
            throw new ConflitoException("Ja existe um cliente com este email");
        }

        if(repository.existsByCpfAndIdNot(dados.cpf(), id)) {
            throw new ConflitoException("Ja existe um cliente com este cpf");
        }

        cliente.setNome(dados.nome());
        cliente.setCpf(dados.cpf());
        cliente.setEmail(dados.email());
        cliente.setTelefone(dados.telefone());

        return new ClienteResponseDTO(cliente);
    }

    //delete
    @Transactional
    public void deletar(Long id) {
        Cliente cliente = buscarEntidade(id);
        cliente.setAtivo(false);
    }


}
