package br.com.develcode.api.model;


import br.com.develcode.api.clientes.DadosCliente;
import br.com.develcode.api.clientes.DadosDetalhamentoClientes;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "clientes")
@Entity(name = "Clientes")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Clientes {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public void excluir() {
        this.ativo = false;
    }

    public void atualizarCaracteristicas(DadosDetalhamentoClientes dados) {
        if (dados.nome() != null){
            this.nome = dados.nome();
        }
        if (dados.cep() != null){
            this.cep = dados.cep();
        }
        if (dados.idade() != null){
            this.idade = dados.idade();
        }

    }
}
