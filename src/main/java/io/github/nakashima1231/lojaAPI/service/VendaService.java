package io.github.nakashima1231.lojaAPI.service;

import io.github.nakashima1231.lojaAPI.dto.ItemVenda.ItemRequestDTO;
import io.github.nakashima1231.lojaAPI.dto.Venda.VendaRequestDTO;
import io.github.nakashima1231.lojaAPI.dto.Venda.VendaResponseDTO;
import io.github.nakashima1231.lojaAPI.dto.Vendedor.VendedorRequestDTO;
import io.github.nakashima1231.lojaAPI.dto.Vendedor.VendedorResponseDTO;
import io.github.nakashima1231.lojaAPI.entities.*;
import io.github.nakashima1231.lojaAPI.repository.ClienteRepository;
import io.github.nakashima1231.lojaAPI.repository.ProdutoRepository;
import io.github.nakashima1231.lojaAPI.repository.VendaRepository;
import io.github.nakashima1231.lojaAPI.repository.VendedorRepository;
import io.github.nakashima1231.lojaAPI.util.Status;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VendaService {
    private final VendaRepository repository;
    private final ClienteRepository clienteRepository;
    private final VendedorRepository vendedorRepository;
    private final ProdutoRepository produtoRepository;


    @Transactional
    public VendaResponseDTO criarVenda(VendaRequestDTO dados) {
        // 1. VALIDAR (e já buscar)
        Cliente cliente = buscarClienteAtivo(dados.clienteId());

        Vendedor vendedor = buscarVendedorAtivo(dados.vendedorId());

        Venda venda = new Venda();
        venda.setCliente(cliente);
        venda.setVendedor(vendedor);
        venda.setDataVenda(LocalDateTime.now());
        venda.setStatus(Status.FINALIZADA);

        // 2. CALCULAR total
        for (ItemRequestDTO itemDTO : dados.itens()) {
            Produto produto = produtoRepository.findByIdAndAtivoTrue(itemDTO.produtoId())
                    .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado: " + itemDTO.produtoId()));

            ItemVenda item = new ItemVenda();
            item.setProduto(produto);
            item.setQuantidade(itemDTO.quantidade());
            item.setPrecoUnitario(produto.getPreco());

            item.setVenda(venda);
            venda.getItens().add(item);
        }

        BigDecimal total = venda.getItens().stream()
                .map(item -> item.getPrecoUnitario()
                        .multiply(BigDecimal.valueOf(item.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        venda.setValorTotal(total);
        // 3. BAIXAR ESTOQUE
        for (ItemVenda item : venda.getItens()) {
            item.getProduto().baixarEstoque(item.getQuantidade());
        }


        // 4. SALVAR
        repository.save(venda);
        return new VendaResponseDTO(venda);
    }

    public List<VendaResponseDTO> listarTodos() {
        return repository.findAll().stream().map(VendaResponseDTO::new).toList();
    }

    public VendaResponseDTO buscarPorId(Long id) {
        return repository.findById(id).map(VendaResponseDTO::new).orElseThrow(null);
    }

    public void deletar(Long id) {
        Venda venda = buscarEntidade(id);
        venda.setStatus(Status.CANCELADA);
    }

    private Venda buscarEntidade(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Nao existe uma venda com este id: " + id));
    }

    @Transactional
    public VendaResponseDTO atualizar(VendaRequestDTO dados, Long id) {
        Venda venda = buscarEntidade(id);

        Cliente cliente = buscarClienteAtivo(dados.clienteId());

        Vendedor vendedor = buscarVendedorAtivo(dados.vendedorId());


        venda.setCliente(cliente);
        venda.setVendedor(vendedor);
        venda.setItens(dados.itens());

        return new VendaResponseDTO(venda);
    }

    private Cliente buscarClienteAtivo(Long id) {
        return clienteRepository.findByIdAndAtivoTrue(id).orElseThrow(() -> new RuntimeException("Cliente não encontrado ou inativo: " + id));
    }

    private Vendedor buscarVendedorAtivo(Long id) {
        return vendedorRepository.findByIdAndAtivoTrue(id).orElseThrow(() -> new RuntimeException("Vendedor não encontrado ou inativo: " + id));
    }


    private calcularItens()




}