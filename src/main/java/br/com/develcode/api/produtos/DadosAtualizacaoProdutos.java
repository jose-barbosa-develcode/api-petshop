package br.com.develcode.api.produtos;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DadosAtualizacaoProdutos(
        @NotNull
        Long id,
        String nome,
        String descricao,
        BigDecimal valor,
        Tipos tipos) {

}
