package br.com.develcode.api.produtos;

import br.com.develcode.api.model.Produtos;

import java.math.BigDecimal;

public record DadosListagemProdutos(Long id, String nome, String descricao, BigDecimal valor) {

    public DadosListagemProdutos(Produtos produtos){
        this(produtos.getIdProdutos(), produtos.getNome(), produtos.getDescricao(), produtos.getValor());
    }

}
