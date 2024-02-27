package br.com.develcode.api.clientes;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.br.CPF;

public record DadosCliente(
        @NotBlank
        String nome,

        @NotNull
        @Pattern(regexp = "\\d{8}")
        String cep,
        @NotNull
        int idade


) {
}
