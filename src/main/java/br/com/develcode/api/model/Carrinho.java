package br.com.develcode.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Table(name = "carrinhos")
@Entity(name = "Carrinho")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Carrinho {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal valorTotal;
    private Integer quantidade;

    @OneToOne
    private Clientes clientes;

    @OneToMany(mappedBy = "carrinho")
    private List<ItemCarrinho> itens = new ArrayList<>();


    public void adicionarItem(ItemCarrinho item) {


    }

    public Carrinho(Clientes clientes){
        this.clientes = clientes;
    }
}
