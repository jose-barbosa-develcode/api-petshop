package br.com.develcode.api.produtos;

import br.com.develcode.api.produtos.Tipos;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DadosProdutos(

                            @NotBlank
                            String nome,
                            @NotBlank
                            String descricao,
                            @NotNull
                            BigDecimal valor,
                            @NotNull
                            Tipos tipos) {
}
