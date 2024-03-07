package br.com.develcode.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "compra")
@Entity(name = "Compra")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "carrinho_id")
    private Carrinho carrinho;

    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL)
    private List<ItemCarrinho> itens = new ArrayList<>();



    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Clientes cliente;

    private BigDecimal valorTotal;

    private LocalDateTime dataHoraFinalizacao;

}

