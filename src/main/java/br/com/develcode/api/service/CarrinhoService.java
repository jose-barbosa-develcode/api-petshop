package br.com.develcode.api.service;

import br.com.develcode.api.model.*;
import br.com.develcode.api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
    public class CarrinhoService {
        private final ItemCarrinhoRepository itemCarrinhoRepository;
        private final ProdutosRepository produtosRepository;

        private final ClientesRepository clientesRepository;

        private final CarrinhoRepository carrinhoRepository;
        private final CompraRepository compraRepository;




        public CarrinhoService(ItemCarrinhoRepository itemCarrinhoRepository, ProdutosRepository produtosRepository, ClientesRepository clientesRepository, CarrinhoRepository carrinhoRepository, CompraRepository compraRepository) {
            this.itemCarrinhoRepository = itemCarrinhoRepository;
            this.produtosRepository = produtosRepository;
            this.clientesRepository = clientesRepository;
            this.carrinhoRepository = carrinhoRepository;
            this.compraRepository = compraRepository;
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

        return carrinhoRepository.save(carrinho);
    }
    public List<ItemCarrinho> obterItensDoCarrinho(Long carrinhoId) {
        return itemCarrinhoRepository.findByCarrinhoId(carrinhoId);
    }


    public void descartarCarrinho(Long carrinhoId) {
        Carrinho carrinho = carrinhoRepository.findById(carrinhoId)
                .orElseThrow(() -> new ResourceNotFoundException("Carrinho não encontrado com o ID: " + carrinhoId));

        carrinho.getItens().clear();

        carrinhoRepository.delete(carrinho);
    }



    public void finalizarCompra(Long carrinhoId) {
        Carrinho carrinho = carrinhoRepository.findById(carrinhoId)
                .orElseThrow(() -> new ResourceNotFoundException("Carrinho não encontrado com o ID: " + carrinhoId));

        Compra compra = new Compra();
        compra.setCarrinho(carrinho);
        compra.setCliente(carrinho.getClientes());
        compra.setItens(carrinho.getItens());
        compra.setValorTotal(carrinho.getValorTotal());
        compra.setDataHoraFinalizacao(LocalDateTime.now());


        compraRepository.save(compra);


        carrinho.getItens().clear();


        carrinhoRepository.save(carrinho);
    }




}

