package br.com.develcode.api.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
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
@EqualsAndHashCode(of = "idItensCarrinho")

public class ItemCarrinho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idItensCarrinho;
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

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Clientes cliente;

    @ManyToOne
    @JoinColumn(name = "compra_id")
    private Compra compra;



}