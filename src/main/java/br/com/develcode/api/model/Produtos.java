package br.com.develcode.api.model;

import br.com.develcode.api.produtos.DadosAtualizacaoProdutos;
import br.com.develcode.api.produtos.DadosProdutos;
import br.com.develcode.api.produtos.Tipos;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Table(name = "produtos")
@Entity(name = "Produtos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Produtos {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal valor;
    @Enumerated(EnumType.STRING)


    private Tipos tipos;

    public Produtos(DadosProdutos dados) {
        this.ativo = true;
        this.nome = dados.nome();
        this.descricao = dados.descricao();
        this.valor = dados.valor();
        this.tipos = dados.tipos();
    }

    private Boolean ativo;

    public void atualizarCaracteristicas(DadosAtualizacaoProdutos dados) {
        if (dados.nome() != null){
            this.nome = dados.nome();
        }
        if (dados.descricao() != null){
            this.descricao = dados.descricao();
        }
        if (dados.valor() != null){
            this.valor = dados.valor();
        }
        if (dados.tipos() != null){
            this.tipos = dados.tipos();
        }
        }

    public void excluir() {
        this.ativo = false;
    }
}
