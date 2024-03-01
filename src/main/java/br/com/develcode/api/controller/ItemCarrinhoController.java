package br.com.develcode.api.controller;

import br.com.develcode.api.model.Produtos;
import br.com.develcode.api.repository.ItemCarrinhoRepository;
import br.com.develcode.api.service.ItemCarrinhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/itens")
public class ItemCarrinhoController {

    @Autowired
    private ItemCarrinhoService itemCarrinhoService;

    @PostMapping("/{itemCarrinhoId}/produtos")
    public ResponseEntity<List<Produtos>> buscarProdutoDoItemCarrinho(@PathVariable Long itemCarrinhoId) {
        Produtos produto = itemCarrinhoService.findProdutosByItemCarrinhoId(itemCarrinhoId);
        if (produto != null) {
            return ResponseEntity.ok().body(Collections.singletonList(produto));
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}


