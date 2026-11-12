package io.github.nakashima1231.lojaAPI.service;

import io.github.nakashima1231.lojaAPI.dto.ProdutoDTO.ProdutoRequestDTO;
import io.github.nakashima1231.lojaAPI.dto.ProdutoDTO.ProdutoResponseDTO;
import io.github.nakashima1231.lojaAPI.entities.Produto;
import io.github.nakashima1231.lojaAPI.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
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

    public List<ProdutoResponseDTO> listarTodos() {
        return repository.findByAtivoTrue().stream().map(ProdutoResponseDTO:: new).toList();
    }

    public ProdutoResponseDTO buscarPorId(Long id) {
        return repository.findByIdAndAtivoTrue(id).map(ProdutoResponseDTO::new) .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com o ID: " + id));
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
        produto.setAtivo(false);
    }


    private Produto buscarEntidade(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Produto nao encontrado com o id " + id));
    }

}
