package br.com.develcode.api.service;

import br.com.develcode.api.model.Carrinho;
import br.com.develcode.api.model.Clientes;
import br.com.develcode.api.model.ItemCarrinho;
import br.com.develcode.api.model.Produtos;
import br.com.develcode.api.repository.CarrinhoRepository;
import br.com.develcode.api.repository.ClientesRepository;
import br.com.develcode.api.repository.ItemCarrinhoRepository;
import br.com.develcode.api.repository.ProdutosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
    public class CarrinhoService {
        private final ItemCarrinhoRepository itemCarrinhoRepository;
        private final ProdutosRepository produtosRepository;

        private final ClientesRepository clientesRepository;

        private final CarrinhoRepository carrinhoRepository;




        public CarrinhoService(ItemCarrinhoRepository itemCarrinhoRepository, ProdutosRepository produtosRepository, ClientesRepository clientesRepository, CarrinhoRepository carrinhoRepository) {
            this.itemCarrinhoRepository = itemCarrinhoRepository;
            this.produtosRepository = produtosRepository;
            this.clientesRepository = clientesRepository;
            this.carrinhoRepository = carrinhoRepository;
        }

        public ItemCarrinho adicionarProdutoAoCarrinho(Long idProduto, int quantidade) {
            Produtos produtos = produtosRepository.findById(idProduto)
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

            ItemCarrinho itemCarrinho = new ItemCarrinho();
            itemCarrinho.setProdutos(produtos);
            itemCarrinho.setQuantidade(quantidade);

            return itemCarrinhoRepository.save(itemCarrinho);
        }
        public Carrinho iniciarNovoCarrinho(Long clienteId) {
        Clientes clientes = clientesRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com o id: " + clienteId));

        Carrinho carrinho = new Carrinho();
        carrinho.setClientes(clientes);
        carrinho.setValorTotal(BigDecimal.valueOf(0.00));
            carrinho.setQuantidade(0);

        return carrinhoRepository.save(carrinho);
    }
    }

