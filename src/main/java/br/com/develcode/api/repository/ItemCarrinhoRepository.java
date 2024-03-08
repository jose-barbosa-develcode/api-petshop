package br.com.develcode.api.repository;

import br.com.develcode.api.model.Clientes;
import br.com.develcode.api.model.ItemCarrinho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface ItemCarrinhoRepository extends JpaRepository<ItemCarrinho, Long> {
    ItemCarrinho findById(long id);


    List<ItemCarrinho> findByClienteId(Long clienteId);
    @Query("SELECT i FROM ItemCarrinho i WHERE i.carrinho.idCarrinho = :carrinhoId")
    List<ItemCarrinho> findByCarrinhoId(@Param("carrinhoId") Long carrinhoId);

    @Transactional
    void deleteByClienteId(Long clienteId);
}

