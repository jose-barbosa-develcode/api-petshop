package br.com.develcode.api.controller;

import br.com.develcode.api.carrinho.CarrinhoComCliente;
import br.com.develcode.api.carrinho.CarrinhoComItens;
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
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;
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
    Produtos produtos;
    ItemCarrinho itemCarrinho;



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




    @PostMapping("/clientes/{clienteId}/carrinho/produtos/{produtoId}")
    public ResponseEntity<Map<String, Object>> adicionarProdutoAoCarrinho(@PathVariable Long clienteId, @PathVariable Long produtoId, @RequestParam int quantidade) {

        Clientes cliente = clientesRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com o ID: " + clienteId));

        Produtos produto = produtosRepository.findById(produtoId)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com o ID: " + produtoId));

        List<ItemCarrinho> itensDoCarrinho = itemCarrinhoRepository.findByClienteId(clienteId);
        Optional<ItemCarrinho> itemExistenteOptional = itensDoCarrinho.stream()
                .filter(item -> item.getProdutos().getIdProdutos().equals(produtoId))
                .findFirst();

        if (itemExistenteOptional.isPresent()) {
            ItemCarrinho itemExistente = itemExistenteOptional.get();
            int novaQuantidade = itemExistente.getQuantidade() + quantidade;
            itemExistente.setQuantidade(novaQuantidade);
            itemCarrinhoRepository.save(itemExistente);
        } else {
            ItemCarrinho itemCarrinho = new ItemCarrinho();
            itemCarrinho.setCliente(cliente);
            itemCarrinho.setProdutos(produto);
            itemCarrinho.setQuantidade(quantidade);
            itemCarrinhoRepository.save(itemCarrinho);
        }

        Carrinho carrinho = carrinhoService.buscarCarrinhoPorCliente(clienteId);
        Map<String, Object> response = new HashMap<>();
        response.put("carrinho", carrinho);
        response.put("itens", carrinho.getItens());
        response.put("valorTotal", carrinho.getValorTotal());

        return ResponseEntity.ok(response);
    }


    @PutMapping("/clientes/{clienteId}/carrinho/produtos/{produtoId}")
    public ResponseEntity<Void> subtrairQuantidadeDoProdutoNoCarrinho(@PathVariable Long clienteId, @PathVariable Long produtoId, @RequestParam int quantidade) {

        Clientes cliente = clientesRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com o ID: " + clienteId));


        Produtos produto = produtosRepository.findById(produtoId)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com o ID: " + produtoId));


        List<ItemCarrinho> itensDoCarrinho = itemCarrinhoRepository.findByClienteId(clienteId);
        Optional<ItemCarrinho> itemExistenteOptional = itensDoCarrinho.stream()
                .filter(item -> item.getProdutos().getIdProdutos().equals(produtoId))
                .findFirst();


        if (itemExistenteOptional.isPresent()) {
            ItemCarrinho itemExistente = itemExistenteOptional.get();
            int novaQuantidade = itemExistente.getQuantidade() - quantidade;
            if (novaQuantidade <= 0) {

                itemCarrinhoRepository.delete(itemExistente);
            } else {

                itemExistente.setQuantidade(novaQuantidade);
                itemCarrinhoRepository.save(itemExistente);
            }
            return ResponseEntity.ok().build();
        } else {
            throw new ResourceNotFoundException("Produto não encontrado no carrinho do cliente.");
        }
    }

    @DeleteMapping("/clientes/{clienteId}/carrinho/produtos")
    public ResponseEntity<Void> removerTodosProdutosDoCarrinho(@PathVariable Long clienteId) {

        Clientes cliente = clientesRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com o ID: " + clienteId));

        List<ItemCarrinho> itensDoCarrinho = itemCarrinhoRepository.findByClienteId(clienteId);


        for (ItemCarrinho item : itensDoCarrinho) {
            item.setQuantidade(0);

            item.getProdutos().setIdProdutos(0L);
        }

        // Salva os itens do carrinho atualizados no repositório
        itemCarrinhoRepository.saveAll(itensDoCarrinho);

        return ResponseEntity.ok().build();
    }



    @GetMapping("/clientes/{clienteId}/carrinho")
    public ResponseEntity<Object> visualizarCarrinhoDoCliente(@PathVariable Long clienteId) {


        Clientes cliente = clientesRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com o ID: " + clienteId));

        Carrinho carrinho = cliente.getCarrinho();
        if (carrinho == null) {
            return ResponseEntity.notFound().build();
        }

        List<ItemCarrinho> itensDoCarrinho = itemCarrinhoRepository.findByClienteId(clienteId);


        BigDecimal valorTotal = BigDecimal.ZERO;
        for (ItemCarrinho item : itensDoCarrinho) {
            BigDecimal precoUnitario = item.getProdutos().getValor();
            BigDecimal quantidade = BigDecimal.valueOf(item.getQuantidade());
            valorTotal = valorTotal.add(precoUnitario.multiply(quantidade));
        }

        carrinho.setValorTotal(valorTotal);
        carrinhoRepository.save(carrinho);

        CarrinhoComItens carrinhoComItens = new CarrinhoComItens(carrinho, itensDoCarrinho, valorTotal);


        return ResponseEntity.ok(carrinhoComItens);
    }





    @GetMapping("/clientes/{clienteId}/carrinho/itens")
    public ResponseEntity<List<ItemCarrinho>> visualizarItensDoCarrinho(@PathVariable Long clienteId) {

        Clientes cliente = clientesRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com o ID: " + clienteId));

        List<ItemCarrinho> itensDoCarrinho = itemCarrinhoRepository.findByClienteId(clienteId);


        boolean todosItensZerados = itensDoCarrinho.stream().allMatch(item -> item.getQuantidade() == 0);

        if (todosItensZerados) {
            return ResponseEntity.ok(Collections.emptyList());
        } else {
            return ResponseEntity.ok(itensDoCarrinho);
        }
    }


    @PostMapping("/finalizar/{carrinhoId}")
    public ResponseEntity<String> finalizarCompra(@PathVariable Long carrinhoId) {
        carrinhoService.finalizarCompra(carrinhoId);
        return ResponseEntity.ok("Compra finalizada com sucesso!");
    }


    @DeleteMapping("/carrinhos/{carrinhoId}")
    public ResponseEntity<?> descartarCarrinho1(@PathVariable Long carrinhoId) {
        carrinhoService.limparCarrinho(carrinhoId);
        return ResponseEntity.ok().build();
    }



}




