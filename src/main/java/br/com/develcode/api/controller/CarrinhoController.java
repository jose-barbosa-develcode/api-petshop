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
    public ResponseEntity<Carrinho> iniciarNovoCarrinhoParaCliente(@PathVariable Long clienteId) {
        Carrinho carrinho = carrinhoService.iniciarNovoCarrinho(clienteId);
        return ResponseEntity.ok(carrinho);
    }



    @PostMapping("/clientes/{clienteId}/produto/{produtoId}")
    public ResponseEntity<Map<String, Object>> adicionarProdutoAoCarrinho(@PathVariable Long clienteId, @PathVariable Long produtoId, @RequestParam int quantidade) {
        Clientes cliente = clientesRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com o ID: " + clienteId));

        Produtos produto = produtosRepository.findById(produtoId)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com o ID: " + produtoId));

        Carrinho carrinho = carrinhoService.adicionarProdutoAoCarrinho(cliente, produto, quantidade);

        Map<String, Object> response = new HashMap<>();
        response.put("carrinho", carrinho);
        response.put("itens", carrinho.getItens());
        response.put("valorTotal", carrinho.getValorTotal());

        return ResponseEntity.ok(response);
    }


    @PutMapping("/clientes/{clienteId}/carrinho/produtos/{produtoId}")
    public ResponseEntity<Void> removerProdutoNoCarrinho(@PathVariable Long clienteId, @PathVariable Long produtoId, @RequestParam int quantidade) {
        Clientes cliente = clientesRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com o ID: " + clienteId));

        Produtos produto = produtosRepository.findById(produtoId)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com o ID: " + produtoId));

        carrinhoService.removerProdutoNoCarrinho(cliente, produto, quantidade);

        return ResponseEntity.ok().build();
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

        itemCarrinhoRepository.saveAll(itensDoCarrinho);

        return ResponseEntity.ok().build();
    }




    @GetMapping("/clientes/{clienteId}/carrinho")
    public ResponseEntity<Object> visualizarCarrinhoDoClienteComValorTotal(@PathVariable Long clienteId) {
        CarrinhoComItens carrinhoComItens = carrinhoService.visualizarCarrinhoDoClienteComValorTotal(clienteId);
        return ResponseEntity.ok(carrinhoComItens);
    }


    @GetMapping("/clientes/{clienteId}/carrinho/itens")
    public ResponseEntity<List<ItemCarrinho>> visualizarCarrinhoDoCliente(@PathVariable Long clienteId) {
        List<ItemCarrinho> itensDoCarrinho = carrinhoService.visualizarItensDoCarrinho(clienteId);
        return ResponseEntity.ok(itensDoCarrinho);
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




