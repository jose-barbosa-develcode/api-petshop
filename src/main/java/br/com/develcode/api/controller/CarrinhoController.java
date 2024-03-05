package br.com.develcode.api.controller;

import br.com.develcode.api.carrinho.CarrinhoComCliente;
import br.com.develcode.api.carrinho.ListarCarrinhosCriado;
import br.com.develcode.api.model.Carrinho;
import br.com.develcode.api.model.Clientes;
import br.com.develcode.api.repository.CarrinhoRepository;
import br.com.develcode.api.repository.ClientesRepository;
import br.com.develcode.api.service.CarrinhoService;
import br.com.develcode.api.service.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    public CarrinhoController(CarrinhoService carrinhoService, ClientesRepository clientesRepository) {
        this.carrinhoService = carrinhoService;
        this.clientesRepository = clientesRepository;
    }

    @PostMapping("/{clienteId}/novo")
    public ResponseEntity<Carrinho> iniciarNovoCarrinho(@PathVariable Long clienteId) {
        Carrinho carrinho = carrinhoService.iniciarNovoCarrinho(clienteId);
        return ResponseEntity.ok(carrinho);
    }

//    @GetMapping
//    public ResponseEntity<Page<ListarCarrinhosCriado>> visualizarCarrinhos(@PageableDefault(sort = {"id"}) Pageable paginacao){
//        var page = carrinhoRepository.findAll(paginacao).map(ListarCarrinhosCriado::new);
//        return ResponseEntity.ok(page);
//    }

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





}




