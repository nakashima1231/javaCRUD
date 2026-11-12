package io.github.nakashima1231.lojaAPI.service;

import io.github.nakashima1231.lojaAPI.dto.ProdutoDTO.ProdutoRequestDTO;
import io.github.nakashima1231.lojaAPI.dto.ProdutoDTO.ProdutoResponseDTO;
import io.github.nakashima1231.lojaAPI.entities.Produto;
import io.github.nakashima1231.lojaAPI.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {
    private final ProdutoRepository repository;

    @Transactional
    public ProdutoResponseDTO criarProduto(ProdutoRequestDTO dados) {
        Produto produto = new Produto();

        produto.setNome(dados.nome());
        produto.setDescricao(dados.descricao());
        produto.setPreco(dados.preco());
        produto.setQuantidadeEstoque(dados.quantidadeEstoque());
        produto.setAtivo(true);

        repository.save(produto);
        return new ProdutoResponseDTO(produto);
    }

    public List<ProdutoResponseDTO> listarTudo() {
        return repository.findAll().stream().map(ProdutoResponseDTO:: new).toList();
    }

    public ProdutoResponseDTO buscarPorId(Long id) {
        Produto produto = buscarEntidade(id);
        return new ProdutoResponseDTO(produto);
    }

    @Transactional
    public ProdutoResponseDTO atualizar(ProdutoRequestDTO dados, Long id) {
        Produto produto = buscarEntidade(id);

        produto.setNome(dados.nome());
        produto.setDescricao(dados.descricao());
        produto.setPreco(dados.preco());
        produto.setQuantidadeEstoque(dados.quantidadeEstoque());

        return new ProdutoResponseDTO(produto);
    }

    @Transactional
    public void deletar(Long id) {
        Produto produto = buscarEntidade(id);
        repository.delete(produto);
    }


    private Produto buscarEntidade(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Produto nao encontrado com o id " + id));
    }

}
