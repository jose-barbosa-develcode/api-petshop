package br.com.develcode.api.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Table(name = "item_carrinho")
@Entity(name = "ItemCarrinho")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ItemCarrinho {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantidade;
    private BigDecimal precoUnitario;

    public ItemCarrinho(Integer quantidade, BigDecimal precoUnitario, Carrinho carrinho, Produtos produtos) {
        this.quantidade = quantidade;
        this.precoUnitario = produtos.getValor();
        this.carrinho = carrinho;
        this.produtos = produtos;
    }

    @ManyToOne
    private Carrinho carrinho;

    @ManyToOne
    private Produtos produtos;


}
