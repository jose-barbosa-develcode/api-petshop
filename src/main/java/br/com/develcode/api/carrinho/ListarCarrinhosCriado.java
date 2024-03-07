package br.com.develcode.api.carrinho;

import br.com.develcode.api.model.Carrinho;
import br.com.develcode.api.model.Clientes;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Setter;

import java.math.BigDecimal;



public record ListarCarrinhosCriado(Long idCarrinho, BigDecimal valorTotalCarrinho, Clientes idCliente) {



    public ListarCarrinhosCriado(Carrinho carrinho){
        this(carrinho.getIdCarrinho(), carrinho.getValorTotal(), carrinho.getClientes() );
    }
}
