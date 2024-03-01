package br.com.develcode.api.service;

import br.com.develcode.api.model.ItemCarrinho;
import br.com.develcode.api.model.Produtos;
import br.com.develcode.api.repository.ItemCarrinhoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ItemCarrinhoService {


    @Autowired
    private ItemCarrinhoRepository itemCarrinhoRepository;

    public Produtos findProdutosByItemCarrinhoId(Long itemCarrinhoId) {
        Optional<ItemCarrinho> itemCarrinhoOptional = itemCarrinhoRepository.findById(itemCarrinhoId);
        return itemCarrinhoOptional.map(ItemCarrinho::getProdutos).orElse(null);
    }
}


