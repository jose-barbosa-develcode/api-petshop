package br.com.develcode.api.clientes;

import br.com.develcode.api.model.Clientes;
import br.com.develcode.api.produtos.DadosDetalhamentoProdutos;

public record DadosDetalhamentoClientes(Long id, String nome, Integer idade, String cep ) {

    public DadosDetalhamentoClientes(Clientes clientes){
        this(clientes.getId(), clientes.getNome(), clientes.getIdade(), clientes.getCep());
    }
}
