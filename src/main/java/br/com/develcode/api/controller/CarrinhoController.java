package br.com.develcode.api.controller;


import br.com.develcode.api.carrinho.ItemCarrinhoSelecionados;
import br.com.develcode.api.model.Carrinho;
import br.com.develcode.api.model.ItemCarrinho;
import br.com.develcode.api.produtos.DadosListagemProdutos;
import br.com.develcode.api.repository.CarrinhoRepository;
import br.com.develcode.api.repository.ItemCarrinhoRepository;
import br.com.develcode.api.service.CarrinhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carrinho")
public class CarrinhoController {

    @Autowired
    private CarrinhoRepository repository;



//    @GetMapping
//    public ResponseEntity <List<ItemCarrinhoSelecionados>> listarItens(){
//        List<Carrinho> itens = repository.findAll();
//        List<ItemCarrinho> itens =
//
//
//    }

    @GetMapping
    public ResponseEntity <Page<ItemCarrinhoSelecionados>> listar(@PageableDefault(sort = {"quantidade"}) Pageable paginacao){
        var page = repository.findAll(paginacao).map(ItemCarrinhoSelecionados::new);
        return ResponseEntity.ok(page);

    }

        private final CarrinhoService carrinhoService;

        public CarrinhoController(CarrinhoService carrinhoService) {
            this.carrinhoService = carrinhoService;
        }

        @PostMapping("/adicionar/{idProduto}")
        public ResponseEntity<ItemCarrinho> adicionarProdutoAoCarrinho(@PathVariable Long idProduto, @RequestParam int quantidade) {
            ItemCarrinho itemCarrinho = carrinhoService.adicionarProdutoAoCarrinho(idProduto, quantidade);
            return ResponseEntity.ok(itemCarrinho);
        }

        @Autowired
        private ItemCarrinhoRepository itemCarrinhoRepository;

        @GetMapping("/itens")
        public List<ItemCarrinho> listarItensCarrinho() {
            return itemCarrinhoRepository.findAll();
    }
}



