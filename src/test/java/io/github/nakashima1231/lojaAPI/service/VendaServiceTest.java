package io.github.nakashima1231.lojaAPI.service;

import io.github.nakashima1231.lojaAPI.dto.ItemVenda.ItemRequestDTO;
import io.github.nakashima1231.lojaAPI.dto.Venda.VendaRequestDTO;
import io.github.nakashima1231.lojaAPI.entities.*;
import io.github.nakashima1231.lojaAPI.exceptions.AcessoNegadoException;
import io.github.nakashima1231.lojaAPI.exceptions.RegraNegocioException;
import io.github.nakashima1231.lojaAPI.repository.ClienteRepository;
import io.github.nakashima1231.lojaAPI.repository.ProdutoRepository;
import io.github.nakashima1231.lojaAPI.repository.UsuarioRepository;
import io.github.nakashima1231.lojaAPI.repository.VendaRepository;
import io.github.nakashima1231.lojaAPI.util.Perfil;
import io.github.nakashima1231.lojaAPI.util.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class VendaServiceTest {

    @Mock
    private VendaRepository repository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private VendaService service;

    private Vendedor vendedor;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        vendedor = new Vendedor();
        vendedor.setId(1L);
        vendedor.setAtivo(true);

        usuario = new Usuario();
        usuario.setPerfil(Perfil.VENDEDOR);
        usuario.setVendedor(vendedor);
    }

    @Test
    void cancelar_vendaDeOutroVendedor_lancaAcessoNegado() {
        Vendedor vendedorB = new Vendedor();
        vendedorB.setId(2L);
        vendedorB.setAtivo(true);

        Venda venda = new Venda();
        venda.setVendedor(vendedorB);
        venda.setStatus(Status.FINALIZADA);

        when(repository.findById(10L)).thenReturn(Optional.of(venda));
        when(usuarioRepository.findByEmail("a@loja.com")).thenReturn(Optional.of(usuario));

        assertThatThrownBy(() -> service.cancelar(10L, "a@loja.com"))
                .isInstanceOf(AcessoNegadoException.class);

        assertThat(venda.getStatus()).isEqualTo(Status.FINALIZADA);
    }

    @Test
    void cancelar_vendaPropria_cancelaERepoeEstoque() {
        Produto produto = new Produto();
        produto.setQuantidadeEstoque(5);

        Venda venda = new Venda();
        venda.setVendedor(vendedor);
        venda.setStatus(Status.FINALIZADA);

        ItemVenda item = new ItemVenda();
        item.setProduto(produto);
        item.setQuantidade(2);

        venda.getItens().add(item);

        when(repository.findById(10L)).thenReturn(Optional.of(venda));
        when(usuarioRepository.findByEmail("a@loja.com")).thenReturn(Optional.of(usuario));

        service.cancelar(10L, "a@loja.com");

        assertThat(venda.getStatus()).isEqualTo(Status.CANCELADA);
        assertThat(produto.getQuantidadeEstoque()).isEqualTo(7);
    }

    @Test
    void cancelar_adminVendaDeOutroVendedor_cancelaVenda() {
        Usuario admin = new Usuario();
        admin.setPerfil(Perfil.ADMIN);

        Venda venda = new Venda();
        venda.setStatus(Status.FINALIZADA);
        venda.setVendedor(vendedor);

        when(repository.findById(10L)).thenReturn(Optional.of(venda));
        when(usuarioRepository.findByEmail("admin@loja.com")).thenReturn(Optional.of(admin));
        service.cancelar(10L, "admin@loja.com");

        assertThat(venda.getStatus()).isEqualTo(Status.CANCELADA);
    }

    @Test
    void cancelar_vendaJaCancelada_lancaExcecaoSemReporEstoque() {
        Produto produto = new Produto();
        produto.setQuantidadeEstoque(5);

        ItemVenda item = new ItemVenda();
        item.setProduto(produto);
        item.setQuantidade(2);

        Venda vendaCancelada = new Venda();
        vendaCancelada.setStatus(Status.CANCELADA);
        vendaCancelada.setVendedor(vendedor);
        vendaCancelada.getItens().add(item);

        when(repository.findById(10L)).thenReturn(Optional.of(vendaCancelada));
        when(usuarioRepository.findByEmail("a@loja.com")).thenReturn(Optional.of(usuario));

        assertThatThrownBy(() -> service.cancelar(10L,"a@loja.com"))
                .isInstanceOf(RegraNegocioException.class);

        assertThat(vendaCancelada.getStatus()).isEqualTo(Status.CANCELADA);
        assertThat(produto.getQuantidadeEstoque()).isEqualTo(5);
    }

    @Test
    void criarVenda_estoqueInsuficiente_lancaExcecaoSemSalvar() {
        // ===== ARRANGE: o que já "existe no banco" =====

        // 1. O cliente que vai comprar
        Cliente cliente = new Cliente();
        cliente.setId(1L);

        // 2. O produto, com só 1 unidade em estoque
        Produto produto = new Produto();
        produto.setId(5L);
        produto.setNome("Teclado");
        produto.setPreco(new BigDecimal("10.00"));
        produto.setQuantidadeEstoque(1);

        // 3. Ensinar os mocks a devolver esses objetos
        when(clienteRepository.findByIdAndAtivoTrue(1L)).thenReturn(Optional.of(cliente));
        when(usuarioRepository.findByEmail("a@loja.com")).thenReturn(Optional.of(usuario));
        when(produtoRepository.findByIdAndAtivoTrue(5L)).thenReturn(Optional.of(produto));

        // ===== ARRANGE: o pedido que chega na API =====

        // "Quero comprar para o cliente 1: 2 unidades do produto 5"
        VendaRequestDTO pedido = new VendaRequestDTO(
                1L,
                List.of(new ItemRequestDTO(5L, 2))
        );

        // ===== ACT + ASSERT =====

        // A venda é recusada
        assertThatThrownBy(() -> service.criarVenda(pedido, "a@loja.com"))
                .isInstanceOf(RegraNegocioException.class);

        // O estoque não mudou
        assertThat(produto.getQuantidadeEstoque()).isEqualTo(1);

        // Nada foi salvo
        verify(repository, never()).save(any());
    }

    @Test
    void criarVenda_EstoqueSuficiente_criaVenda() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);

        Produto produto = new Produto();
        produto.setId(5L);
        produto.setNome("Teclado");
        produto.setPreco(new BigDecimal("10.00"));
        produto.setQuantidadeEstoque(10);

        when(clienteRepository.findByIdAndAtivoTrue(1L)).thenReturn(Optional.of(cliente));
        when(usuarioRepository.findByEmail("a@loja.com")).thenReturn(Optional.of(usuario));
        when(produtoRepository.findByIdAndAtivoTrue(5L)).thenReturn(Optional.of(produto));

        VendaRequestDTO pedido = new VendaRequestDTO(
                1L,
                List.of(new ItemRequestDTO(5L, 2))
        );

        service.criarVenda(pedido, "a@loja.com");

        assertThat(produto.getQuantidadeEstoque()).isEqualTo(8);

        verify(repository).save(any());
    }



}