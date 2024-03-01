package br.com.develcode.api.model;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Table(name = "item_carrinho")
@Entity(name = "ItemCarrinho")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ItemCarrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantidade;
    private BigDecimal precoUnitario;

    public ItemCarrinho(Integer quantidade, BigDecimal precoUnitario, Carrinho carrinho, Produtos produtos) {
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.carrinho = carrinho;
        this.produtos = produtos;
    }

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produtos produtos;


    @ManyToOne
    @JoinColumn(name = "carrinho_id")
    private Carrinho carrinho;


}