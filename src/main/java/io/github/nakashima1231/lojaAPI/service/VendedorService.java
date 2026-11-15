package io.github.nakashima1231.lojaAPI.service;

import io.github.nakashima1231.lojaAPI.dto.Vendedor.VendedorRequestDTO;
import io.github.nakashima1231.lojaAPI.dto.Vendedor.VendedorResponseDTO;
import io.github.nakashima1231.lojaAPI.entities.Vendedor;
import io.github.nakashima1231.lojaAPI.exceptions.*;
import io.github.nakashima1231.lojaAPI.repository.VendedorRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VendedorService {
    private final VendedorRepository repository;

    private Vendedor buscarEntidade(Long id) {
        return repository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Nao existe um vendedor com este id: " + id));
    }

    public List<VendedorResponseDTO> listarTodos() {
        return repository.findByAtivoTrue().stream().map(VendedorResponseDTO::new).toList();
    }

    public VendedorResponseDTO buscarPorId(Long id) {
        return repository.findByIdAndAtivoTrue(id).map(VendedorResponseDTO::new).orElseThrow(() -> new RecursoNaoEncontradoException("Nao existe um vendedor com este id: " + id));
    }

    @Transactional
    public VendedorResponseDTO criarVendedor(VendedorRequestDTO dados) {
        if(repository.existsByEmail(dados.email())) {
            throw new RecursoNaoEncontradoException("Ja existe um vendedor com este email!");
        }

        if(repository.existsByMatricula(dados.matricula())) {
            throw new RecursoNaoEncontradoException("Ja existe um vendedor com esta matricula");
        }

        Vendedor vendedor = new Vendedor();
        vendedor.setNome(dados.nome());
        vendedor.setEmail(dados.email());
        vendedor.setMatricula(dados.matricula());
        vendedor.setPercentualComissao(dados.percentualComissao());
        vendedor.setAtivo(true);

        repository.save(vendedor);

        return new VendedorResponseDTO(vendedor);
    }

    @Transactional
    public VendedorResponseDTO atualizar(VendedorRequestDTO dados, Long id) {
        Vendedor vendedor = buscarEntidade(id);

        if(repository.existsByEmailAndIdNot(dados.email(), id)) {
            throw new RecursoNaoEncontradoException("Ja existe um vendedor com este email!");
        }

        if(repository.existsByMatriculaAndIdNot(dados.matricula(), id)) {
            throw new RecursoNaoEncontradoException("Ja existe um vendedor com esta matricula!");
        }

        vendedor.setNome(dados.nome());
        vendedor.setEmail(dados.email());
        vendedor.setMatricula(dados.matricula());
        vendedor.setPercentualComissao(dados.percentualComissao());

        return new VendedorResponseDTO(vendedor);
    }

    @Transactional
    public void deletar(Long id) {
        Vendedor vendedor = buscarEntidade(id);
        vendedor.setAtivo(false);
    }


}
