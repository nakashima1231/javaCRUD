package io.github.nakashima1231.lojaAPI.service;

import io.github.nakashima1231.lojaAPI.dto.ItemVenda.ItemRequestDTO;
import io.github.nakashima1231.lojaAPI.dto.Venda.VendaRequestDTO;
import io.github.nakashima1231.lojaAPI.dto.Venda.VendaResponseDTO;
import io.github.nakashima1231.lojaAPI.entities.*;
import io.github.nakashima1231.lojaAPI.exceptions.*;
import io.github.nakashima1231.lojaAPI.repository.ClienteRepository;
import io.github.nakashima1231.lojaAPI.repository.ProdutoRepository;
import io.github.nakashima1231.lojaAPI.repository.VendaRepository;
import io.github.nakashima1231.lojaAPI.repository.VendedorRepository;
import io.github.nakashima1231.lojaAPI.util.Status;
import jakarta.persistence.EntityNotFoundException;
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

    private void adicionarItens(Venda venda, List<ItemRequestDTO> itensDTO) {
        for (ItemRequestDTO itemDTO : itensDTO) {
            Produto produto = produtoRepository.findByIdAndAtivoTrue(itemDTO.produtoId())
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado: " + itemDTO.produtoId()));

            ItemVenda item = new ItemVenda();
            item.setProduto(produto);
            item.setQuantidade(itemDTO.quantidade());
            item.setPrecoUnitario(produto.getPreco());

            item.setVenda(venda);
            venda.getItens().add(item);
        }
    }


    private BigDecimal calcularTotal(Venda venda) {
        return venda.getItens().stream()
                .map(item -> item.getPrecoUnitario()
                        .multiply(BigDecimal.valueOf(item.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    private void baixarEstoque(Venda venda) {
        for (ItemVenda item : venda.getItens()) {
            item.getProduto().baixarEstoque(item.getQuantidade());
        }
    }

    @Transactional
    public VendaResponseDTO criarVenda(VendaRequestDTO dados, String email) {
        // 1. VALIDAR (e já buscar)
        Cliente cliente = buscarClienteAtivo(dados.clienteId());

        Vendedor vendedor = vendedorRepository.finbyEmail(email).orElseThrow(() -> new RecursoNaoEncontradoException("Vendedor nao encontrado"));;

        Venda venda = new Venda();
        venda.setCliente(cliente);
        venda.setVendedor(vendedor);
        venda.setDataVenda(LocalDateTime.now());
        venda.setStatus(Status.FINALIZADA);

        adicionarItens(venda, dados.itens());

        venda.setValorTotal(calcularTotal(venda));

        baixarEstoque(venda);

        repository.save(venda);
        return new VendaResponseDTO(venda);
    }


    @Transactional(readOnly = true)
    public List<VendaResponseDTO> listarTodos() {
        return repository.findAll().stream().map(VendaResponseDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public VendaResponseDTO buscarPorId(Long id) {
        return new VendaResponseDTO(buscarEntidade(id));
    }

    @Transactional
    public void cancelar(Long id) {
        Venda venda = buscarEntidade(id);

        if (venda.getStatus() == Status.CANCELADA) {
            throw new RegraNegocioException("Venda já está cancelada");
        }

        for (ItemVenda item : venda.getItens()) {
            item.getProduto().reporEstoque(item.getQuantidade());   // o "contrário" do baixarEstoque
        }
        venda.setStatus(Status.CANCELADA);
    }

    private Venda buscarEntidade(Long id) {
        return repository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Nao existe uma venda com este id: " + id));
    }


    private Cliente buscarClienteAtivo(Long id) {
        return clienteRepository.findByIdAndAtivoTrue(id).orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado ou inativo: " + id));
    }

    private Vendedor buscarVendedorAtivo(Long id) {
        return vendedorRepository.findByIdAndAtivoTrue(id).orElseThrow(() -> new RecursoNaoEncontradoException("Vendedor não encontrado ou inativo: " + id));
    }


}