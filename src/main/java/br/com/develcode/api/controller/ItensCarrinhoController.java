package br.com.develcode.api.controller;


import br.com.develcode.api.carrinho.ItemCarrinhoSelecionados;
import br.com.develcode.api.model.ItemCarrinho;
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
@RequestMapping("/itenscarrinho")
public class ItensCarrinhoController {

    @Autowired
    private CarrinhoRepository repository;


        private final CarrinhoService carrinhoService;

        public ItensCarrinhoController(CarrinhoService carrinhoService) {
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



