package br.com.develcode.api.produtos;

import br.com.develcode.api.model.Produtos;

import java.math.BigDecimal;

public record DadosDetalhamentoProdutos(Long id, String nome, String descricao, BigDecimal valor, Tipos tipos) {
    public DadosDetalhamentoProdutos(Produtos produtos){
        this(produtos.getIdProdutos(), produtos.getNome(), produtos.getDescricao(), produtos.getValor(), produtos.getTipos());
    }
}
