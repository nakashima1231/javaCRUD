package io.github.nakashima1231.lojaAPI.service;

import io.github.nakashima1231.lojaAPI.dto.ClienteDTO.ClienteRequestDTO;
import io.github.nakashima1231.lojaAPI.dto.ClienteDTO.ClienteResponseDTO;
import io.github.nakashima1231.lojaAPI.entities.Cliente;
import io.github.nakashima1231.lojaAPI.exceptions.ClienteNotFoundException;
import io.github.nakashima1231.lojaAPI.repository.ClienteRepository;
import io.github.nakashima1231.lojaAPI.util.CpfValidator;
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
        Cliente cliente = repository.findById(id).orElseThrow(() -> new ClienteNotFoundException(id));
        return cliente;
    }


    public List<ClienteResponseDTO> listarTodos() {
        return repository.findAll().stream().map(ClienteResponseDTO:: new).toList();
    }

    public ClienteResponseDTO buscarPorId(Long id) {
        Cliente cliente = buscarEntidade(id);
        return new ClienteResponseDTO(cliente);
    }

    //post
    @Transactional
    public ClienteResponseDTO criarCliente(ClienteRequestDTO dados) {
        if(!CpfValidator.validarCpf(dados.cpf())) {
            throw new RuntimeException("CPF invalido");
        }

        if(repository.existsByCpf(dados.cpf())) {
            throw new RuntimeException("Ja existe um cliente com este cpf");
        }

        if(repository.existsByEmail(dados.email())) {
            throw new RuntimeException("Ja existe um cliente com este email");
        }


        Cliente novo = new Cliente();
        novo.setNome(dados.nome());
        novo.setCpf(dados.cpf());
        novo.setEmail(dados.email());
        novo.setTelefone(dados.telefone());
        novo.setDataCadastro(LocalDateTime.now());

        repository.save(novo);

        return new ClienteResponseDTO(novo);
    }

    //put
    @Transactional
    public ClienteResponseDTO atualizar(ClienteRequestDTO dados, Long id) {

        if(!CpfValidator.validarCpf(dados.cpf())) {
            throw new RuntimeException("CPF invalido");
        }

        if(repository.existsByCpfAndIdNot(dados.cpf(), id)) {
            throw new RuntimeException("Ja existe um cliente com este cpf");
        }


        Cliente cliente = buscarEntidade(id);
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
        repository.delete(cliente);
    }


}
