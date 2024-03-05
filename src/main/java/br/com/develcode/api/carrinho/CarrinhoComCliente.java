package br.com.develcode.api.carrinho;

import br.com.develcode.api.model.Carrinho;

public record CarrinhoComCliente(Carrinho carrinho, String nomeCliente) {}

