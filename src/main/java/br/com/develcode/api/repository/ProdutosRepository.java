package br.com.develcode.api.repository;

import br.com.develcode.api.model.Produtos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutosRepository extends JpaRepository<Produtos, Long> {

    Page<Produtos> findAllByAtivoTrue(Pageable paginacao);
}
