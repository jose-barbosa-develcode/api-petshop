package br.com.develcode.api.repository;

import br.com.develcode.api.model.Carrinho;
import br.com.develcode.api.model.Compra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompraRepository extends JpaRepository<Compra, Long> {
}
