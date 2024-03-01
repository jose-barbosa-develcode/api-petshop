package br.com.develcode.api.repository;

import br.com.develcode.api.model.ItemCarrinho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemCarrinhoRepository extends JpaRepository<ItemCarrinho, Long> {
    ItemCarrinho findById(long id);

}
