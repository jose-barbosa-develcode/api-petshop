package br.com.develcode.api.carrinho;

import br.com.develcode.api.model.Carrinho;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



public record CarrinhoComCliente(Carrinho carrinho, java.util.List<br.com.develcode.api.model.ItemCarrinho> itensDoCarrinho) {}

