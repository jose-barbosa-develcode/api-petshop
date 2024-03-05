package br.com.develcode.api.controller;

import br.com.develcode.api.carrinho.CarrinhoComCliente;
import br.com.develcode.api.model.Carrinho;
import br.com.develcode.api.model.Clientes;
import br.com.develcode.api.model.ItemCarrinho;
import br.com.develcode.api.model.Produtos;
import br.com.develcode.api.repository.CarrinhoRepository;
import br.com.develcode.api.repository.ClientesRepository;
import br.com.develcode.api.repository.ItemCarrinhoRepository;
import br.com.develcode.api.repository.ProdutosRepository;
import br.com.develcode.api.service.CarrinhoService;
import br.com.develcode.api.service.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/carrinho")
public class CarrinhoController {

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    private final CarrinhoService carrinhoService;
    private ClientesRepository clientesRepository;
    private ProdutosRepository produtosRepository;

    private ItemCarrinhoRepository itemCarrinhoRepository;



    public CarrinhoController(CarrinhoService carrinhoService, ClientesRepository clientesRepository, ItemCarrinhoRepository itemCarrinhoRepository, ProdutosRepository produtosRepository) {
        this.carrinhoService = carrinhoService;
        this.clientesRepository = clientesRepository;
        this.itemCarrinhoRepository = itemCarrinhoRepository;
        this.produtosRepository = produtosRepository;
    }

    @PostMapping("/{clienteId}/novo")
    public ResponseEntity<Carrinho> iniciarNovoCarrinho(@PathVariable Long clienteId) {
        Carrinho carrinho = carrinhoService.iniciarNovoCarrinho(clienteId);
        return ResponseEntity.ok(carrinho);
    }

    @GetMapping("/carrinhos")
    public List<Carrinho> visualizarCarrinhos() {
        List<Carrinho> carrinhos = carrinhoRepository.findAll();

        Set<Clientes> clientes = carrinhos.stream()
                .map(Carrinho::getClientes)
                .collect(Collectors.toSet());


        clientes.forEach(cliente -> cliente.setCarrinho(null));

        return carrinhos;
    }

    @GetMapping("/clientes/{clienteId}/carrinho")
    public ResponseEntity<CarrinhoComCliente> visualizarCarrinhoDoCliente(@PathVariable Long clienteId) {
        Optional<Clientes> clienteOptional = clientesRepository.findById(clienteId);

        if (clienteOptional.isEmpty()) {
            throw new ResourceNotFoundException("Cliente não encontrado com o ID: " + clienteId);
        }

        Clientes cliente = clienteOptional.get();
        Carrinho carrinho = cliente.getCarrinho();

        if (carrinho == null) {
            return ResponseEntity.notFound().build();
        }

        String nomeCliente = cliente.getNome();
        CarrinhoComCliente carrinhoComCliente = new CarrinhoComCliente(carrinho, nomeCliente);

        return ResponseEntity.ok(carrinhoComCliente);
    }


    @PostMapping("/clientes/{clienteId}/carrinho/produtos/{produtoId}")
    public ResponseEntity<Void> adicionarProdutoAoCarrinho(@PathVariable Long clienteId, @PathVariable Long produtoId, @RequestParam int quantidade) {

        Clientes cliente = clientesRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com o ID: " + clienteId));


        Produtos produto = produtosRepository.findById(produtoId)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com o ID: " + produtoId));


        ItemCarrinho itemCarrinho = new ItemCarrinho();
        itemCarrinho.setCliente(cliente);
        itemCarrinho.setProdutos(produto);
        itemCarrinho.setQuantidade(quantidade);


        itemCarrinhoRepository.save(itemCarrinho);


        return ResponseEntity.ok().build();
    }

    @GetMapping("/clientes/{clienteId}/carrinho/itens")
    public ResponseEntity<List<ItemCarrinho>> visualizarItensDoCarrinho(@PathVariable Long clienteId) {

        Clientes cliente = clientesRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com o ID: " + clienteId));


        List<ItemCarrinho> itensDoCarrinho = itemCarrinhoRepository.findByClienteId(clienteId);


        return ResponseEntity.ok(itensDoCarrinho);
    }






}




