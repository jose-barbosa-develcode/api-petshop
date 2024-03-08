package br.com.develcode.api.model;


import br.com.develcode.api.clientes.DadosCliente;
import br.com.develcode.api.clientes.DadosDetalhamentoClientes;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "clientes")
@Entity(name = "Clientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Clientes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cep;
    private int idade;

    private Boolean ativo;


    public Clientes(DadosCliente dadosCliente) {
        this.nome = dadosCliente.nome();
        this.cep = dadosCliente.cep();
        this.idade = dadosCliente.idade();
        this.ativo = true;
    }



    @OneToOne(mappedBy = "clientes", cascade = CascadeType.ALL)
    private Carrinho carrinho;

    @JsonIgnore
    @OneToMany(mappedBy = "clientes")




    public void excluir() {
        this.ativo = false;
    }

    public void atualizarCaracteristicas(DadosDetalhamentoClientes dados) {
        if (dados.nome() != null) {
            this.nome = dados.nome();
        }
        if (dados.cep() != null) {
            this.cep = dados.cep();
        }
        if (dados.idade() != null) {
            this.idade = dados.idade();
        }

    }



}

