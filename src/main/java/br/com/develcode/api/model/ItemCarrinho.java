package br.com.develcode.api.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

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
    @JsonIgnore
    private Long idItensCarrinho;
    private Integer quantidade;
    @JsonIgnore
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
    @JsonIgnore
    private Carrinho carrinho;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    @JsonIgnore
    private Clientes cliente;

    @ManyToOne
    @JoinColumn(name = "compra_id")
    private Compra compra;



}