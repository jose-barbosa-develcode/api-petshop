package br.com.develcode.api.service;

import br.com.develcode.api.carrinho.CarrinhoComItens;
import br.com.develcode.api.model.*;
import br.com.develcode.api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

         public Carrinho iniciarNovoCarrinho(Long clienteId) {
             Clientes clientes = clientesRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com o id: " + clienteId));

         Carrinho carrinho = new Carrinho();
         carrinho.setClientes(clientes);
         carrinho.setValorTotal(BigDecimal.valueOf(0.00));

        return carrinhoRepository.save(carrinho);
    }

    public Carrinho adicionarProdutoAoCarrinho(Clientes cliente, Produtos produto, int quantidade) {
        List<ItemCarrinho> itensDoCarrinho = itemCarrinhoRepository.findByClienteId(cliente.getId());
        Optional<ItemCarrinho> itemExistenteOptional = itensDoCarrinho.stream()
                .filter(item -> item.getProdutos().getIdProdutos().equals(produto.getIdProdutos()))
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

        return buscarCarrinhoPorCliente(cliente.getId());
    }

    public void removerProdutoNoCarrinho(Clientes cliente, Produtos produto, int quantidade) {
        List<ItemCarrinho> itensDoCarrinho = itemCarrinhoRepository.findByClienteId(cliente.getId());

        Optional<ItemCarrinho> itemExistenteOptional = itensDoCarrinho.stream()
                .filter(item -> item.getProdutos().getIdProdutos().equals(produto.getIdProdutos()))
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
        } else {
            throw new ResourceNotFoundException("Produto não encontrado no carrinho do cliente.");
        }
    }

    public CarrinhoComItens visualizarCarrinhoDoClienteComValorTotal(Long clienteId) {
        Clientes cliente = clientesRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com o ID: " + clienteId));

        Carrinho carrinho = cliente.getCarrinho();
        if (carrinho == null) {
            throw new ResourceNotFoundException("Carrinho não encontrado para o cliente com o ID: " + clienteId);
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

        return new CarrinhoComItens(carrinho, itensDoCarrinho, valorTotal);
    }

    public List<ItemCarrinho> visualizarItensDoCarrinho(Long clienteId) {
        Clientes cliente = clientesRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com o ID: " + clienteId));

        List<ItemCarrinho> itensDoCarrinho = itemCarrinhoRepository.findByClienteId(clienteId);

        boolean todosItensZerados = itensDoCarrinho.stream().allMatch(item -> item.getQuantidade() == 0);

        if (todosItensZerados) {
            return Collections.emptyList();
        } else {
            return itensDoCarrinho;
        }
    }








    public List<ItemCarrinho> obterItensDoCarrinho(Long carrinhoId) {
        return itemCarrinhoRepository.findByCarrinhoId(carrinhoId);
    }


    public void descartarCarrinho(Long carrinhoId) {
        Carrinho carrinho = carrinhoRepository.findById(carrinhoId)
                .orElseThrow(() -> new ResourceNotFoundException("Carrinho não encontrado com o ID: " + carrinhoId));
        
        carrinho.getItens().clear();
        carrinho.setValorTotal(BigDecimal.ZERO);
        carrinhoRepository.save(carrinho);
    }

    public void finalizarCompra(Long carrinhoId) {
        Carrinho carrinho = carrinhoRepository.findById(carrinhoId)
                .orElseThrow(() -> new ResourceNotFoundException("Carrinho não encontrado com o ID: " + carrinhoId));


        BigDecimal valorTotal = calcularValorTotal(carrinho);


        Compra compra = new Compra();
        compra.setCarrinho(carrinho);
        compra.setCliente(carrinho.getClientes());
        compra.setValorTotal(valorTotal);
        compra.setDataHoraFinalizacao(LocalDateTime.now());


        compraRepository.save(compra);


        carrinhoRepository.delete(carrinho);
    }

    private BigDecimal calcularValorTotal(Carrinho carrinho) {
        BigDecimal valorTotal = BigDecimal.ZERO;
        for (ItemCarrinho item : carrinho.getItens()) {
            valorTotal = valorTotal.add(item.getPrecoUnitario().multiply(BigDecimal.valueOf(item.getQuantidade())));
        }
        return valorTotal;
    }



    public Carrinho buscarCarrinhoPorCliente(Long clienteId) {
        return carrinhoRepository.findByClienteId(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Carrinho não encontrado para o cliente com o ID: " + clienteId));
    }


    public void limparCarrinho(Long carrinhoId) {
        Carrinho carrinho = carrinhoRepository.findById(carrinhoId)
                .orElseThrow(() -> new ResourceNotFoundException("Carrinho não encontrado com o ID: " + carrinhoId));

        carrinho.getItens().clear();
        carrinho.setValorTotal(BigDecimal.ZERO);

        carrinhoRepository.save(carrinho);
    }



}

