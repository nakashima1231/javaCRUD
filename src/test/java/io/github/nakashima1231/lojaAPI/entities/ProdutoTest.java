package io.github.nakashima1231.lojaAPI.entities;

import io.github.nakashima1231.lojaAPI.exceptions.RegraNegocioException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProdutoTest {

    @Test
    void baixarEstoque_comEstoqueSuficiente_diminuiAQuantidade(){
        Produto produto = new Produto();
        produto.setQuantidadeEstoque(10);

        produto.baixarEstoque(3);

        assertThat(produto.getQuantidadeEstoque()).isEqualTo(7);
    }

    @Test
    void baixarEstoque_comEstoqueExato_diminuiAQuantidade() {
        Produto produto = new Produto();
        produto.setQuantidadeEstoque(10);

        produto.baixarEstoque(10);

        assertThat(produto.getQuantidadeEstoque()).isEqualTo(0);
    }

    @Test
    void baixarEstoque_comEstoqueInsuficiente_lancaRegraNegocioException() {
        Produto produto = new Produto();
        produto.setQuantidadeEstoque(10);

        assertThatThrownBy(() -> produto.baixarEstoque(20))
                .isInstanceOf(RegraNegocioException.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -10})
    void baixarEstoque_comQuantidadeInvalida_lancaIllegalArgumentException(int quantidade) {
        Produto produto = new Produto();
        produto.setQuantidadeEstoque(10);

        assertThatThrownBy(() -> produto.baixarEstoque(quantidade))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void reporEstoque_comQuantidadeValida_aumentaOEstoque() {
        Produto produto = new Produto();
        produto.setQuantidadeEstoque(0);

        produto.reporEstoque(10);

        assertThat(produto.getQuantidadeEstoque()).isEqualTo(10);
    }

    @Test
    void reporEstoque_comQuantidadeNegativa_lancaIllegalArgumentException() {
        Produto produto = new Produto();
        produto.setQuantidadeEstoque(0);

        assertThatThrownBy(() -> produto.reporEstoque(-10))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
