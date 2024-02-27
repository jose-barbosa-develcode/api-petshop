package br.com.develcode.api.repository;

import br.com.develcode.api.model.Clientes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientesRepository extends JpaRepository<Clientes, Long> {

    Page<Clientes> findAllByAtivoTrue(Pageable paginacao);
}
