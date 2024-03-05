package br.com.develcode.api.repository;

import br.com.develcode.api.model.Carrinho;
import br.com.develcode.api.model.ItemCarrinho;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {

    Carrinho findById(long id);

}
