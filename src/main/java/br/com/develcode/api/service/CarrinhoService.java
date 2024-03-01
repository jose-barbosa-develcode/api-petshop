package br.com.develcode.api.service;

import br.com.develcode.api.model.ItemCarrinho;
import br.com.develcode.api.model.Produtos;
import br.com.develcode.api.repository.ItemCarrinhoRepository;
import br.com.develcode.api.repository.ProdutosRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
    public class CarrinhoService {
        private final ItemCarrinhoRepository itemCarrinhoRepository;
        private final ProdutosRepository produtosRepository;

        public CarrinhoService(ItemCarrinhoRepository itemCarrinhoRepository, ProdutosRepository produtosRepository) {
            this.itemCarrinhoRepository = itemCarrinhoRepository;
            this.produtosRepository = produtosRepository;
        }

        public ItemCarrinho adicionarProdutoAoCarrinho(Long idProduto, int quantidade) {
            Produtos produtos = produtosRepository.findById(idProduto)
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

            ItemCarrinho itemCarrinho = new ItemCarrinho();
            itemCarrinho.setProdutos(produtos);
            itemCarrinho.setQuantidade(quantidade);

            return itemCarrinhoRepository.save(itemCarrinho);
        }
    }

