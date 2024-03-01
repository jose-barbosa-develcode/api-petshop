package br.com.develcode.api.service;


import br.com.develcode.api.model.Carrinho;
import br.com.develcode.api.model.Clientes;
import br.com.develcode.api.repository.CarrinhoRepository;
import br.com.develcode.api.repository.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {

    private Carrinho carrinho;


    @Autowired
    private ClientesRepository clienteRepository;

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Transactional
    public Clientes criarClienteComCarrinho(Clientes cliente) {
        Carrinho carrinho = new Carrinho();
        carrinho.setClientes(cliente);

        cliente.setCarrinho(carrinho);

        clienteRepository.save(cliente);

        return cliente;
    }


}

