package br.com.develcode.api.carrinho;

import br.com.develcode.api.model.Carrinho;
import br.com.develcode.api.model.ItemCarrinho;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.util.List;


public record CarrinhoComItens(Carrinho carrinho, List<ItemCarrinho> itens, BigDecimal valorTotal) {
}
