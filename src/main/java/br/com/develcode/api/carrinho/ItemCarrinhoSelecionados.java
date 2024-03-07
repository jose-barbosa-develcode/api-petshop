package br.com.develcode.api.carrinho;

import br.com.develcode.api.model.Carrinho;
import br.com.develcode.api.model.ItemCarrinho;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;


public record ItemCarrinhoSelecionados(Long idProdutos, BigDecimal valorTotal) {


    public ItemCarrinhoSelecionados(Carrinho carrinho) {
        this(carrinho.getIdCarrinho(), carrinho.getValorTotal());

    }
}
