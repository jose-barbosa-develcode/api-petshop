package br.com.develcode.api.carrinho;

import br.com.develcode.api.model.Carrinho;
import br.com.develcode.api.model.Clientes;
import lombok.Setter;

import java.math.BigDecimal;


public record ListarCarrinhosCriado(Long idCarrinho, BigDecimal valorTotalCarrinho, Clientes idCliente) {



    public ListarCarrinhosCriado(Carrinho carrinho){
        this(carrinho.getId(), carrinho.getValorTotal(), carrinho.getClientes() );
    }
}
