package br.com.develcode.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @OneToOne @JoinColumn(name = "cliente_id")
    @JsonIgnoreProperties("carrinho")
    private Clientes clientes;


    @OneToMany(mappedBy = "carrinho", cascade = CascadeType.ALL)
    private List<ItemCarrinho> itens = new ArrayList<>();



//    public void adicionarItem(ItemCarrinho item) {
//        this.itens.add(item);
//
//    }
//
//    public Carrinho(Clientes clientes){
//        this.clientes = clientes;
//    }
}




