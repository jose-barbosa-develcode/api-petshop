package br.com.develcode.api.repository;

import br.com.develcode.api.model.Carrinho;
import br.com.develcode.api.model.Clientes;
import br.com.develcode.api.model.ItemCarrinho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {

    Carrinho findById(long id);

    List<Carrinho> findByClientes(Clientes cliente);

    @Transactional
    void deleteById(Long id);


    @Query("SELECT c FROM Carrinho c WHERE c.clientes.id = ?1")
    Optional<Carrinho> findByClienteId(Long clienteId);

}
