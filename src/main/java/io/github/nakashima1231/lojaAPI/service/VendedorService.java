package io.github.nakashima1231.lojaAPI.service;

import io.github.nakashima1231.lojaAPI.dto.Vendedor.VendedorRequestDTO;
import io.github.nakashima1231.lojaAPI.dto.Vendedor.VendedorResponseDTO;
import io.github.nakashima1231.lojaAPI.entities.Vendedor;
import io.github.nakashima1231.lojaAPI.repository.VendedorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VendedorService {
    private final VendedorRepository repository;

    private Vendedor buscarEntidade(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Nao existe um vendedor com este id: " + id));
    }

    public List<VendedorResponseDTO> listarTodos() {
        return repository.findAll().stream().map(VendedorResponseDTO::new).toList();
    }

    public VendedorResponseDTO buscarPorId(Long id) {
        Vendedor vendedor = buscarEntidade(id);
        return new VendedorResponseDTO(vendedor);
    }

    @Transactional
    public VendedorResponseDTO criar(VendedorRequestDTO dados) {
        if(repository.existsByEmail(dados.email())) {
            throw new RuntimeException("Ja existe um vendedor com este email!");
        }

        if(repository.existsByMatricula(dados.matricula())) {
            throw new RuntimeException("Ja existe um vendedor com esta matricula");
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

        vendedor.setNome(dados.nome());
        vendedor.setEmail(dados.email());
        vendedor.setMatricula(dados.matricula());
        vendedor.setPercentualComissao(dados.percentualComissao());

        return new VendedorResponseDTO(vendedor);
    }

    @Transactional
    public void excluir(Long id) {
        Vendedor vendedor = buscarEntidade(id);
        repository.delete(vendedor);
    }


}
