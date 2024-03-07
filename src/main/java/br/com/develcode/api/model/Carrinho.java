package br.com.develcode.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Table(name = "carrinhos")
@Entity(name = "Carrinho")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idCarrinho")
public class Carrinho {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCarrinho;
    private BigDecimal valorTotal;

    @OneToOne @JoinColumn(name = "cliente_id")
    @JsonIgnoreProperties("carrinho")
    private Clientes clientes;


    public Long getId() {
        return idCarrinho;
    }

    @OneToMany(mappedBy = "carrinho", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ItemCarrinho> itens;


}




