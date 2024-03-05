package br.com.develcode.api.controller;


import br.com.develcode.api.clientes.DadosCliente;
import br.com.develcode.api.clientes.DadosDetalhamentoClientes;
import br.com.develcode.api.model.Clientes;
import br.com.develcode.api.produtos.DadosAtualizacaoProdutos;
import br.com.develcode.api.produtos.DadosDetalhamentoProdutos;
import br.com.develcode.api.repository.ClientesRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController @RequestMapping("/clientes")
public class ClientesController {

    @Autowired
    private ClientesRepository repository;


    @PostMapping
    @Transactional
    public ResponseEntity cadastrarClientes(@RequestBody @Valid DadosCliente dadosCliente, UriComponentsBuilder uriBuilder){
        var clientes = new Clientes(dadosCliente);
        repository.save(new Clientes((dadosCliente)));

        var uri = uriBuilder.path("/clientes/{id}").buildAndExpand(clientes.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoClientes(clientes));

    }

    @GetMapping
    public ResponseEntity <Page<DadosDetalhamentoClientes>> listar(@PageableDefault(sort = {"Idade"})Pageable paginacao){
        var page = repository.findAllByAtivoTrue(paginacao).map((DadosDetalhamentoClientes::new));
        return  ResponseEntity.ok(page);
    }

    @DeleteMapping("/{id}") @Transactional
    public ResponseEntity excluirClientes(@PathVariable Long id){
        var produtos = repository.getReferenceById((id));
        produtos.excluir();

        return ResponseEntity.noContent().build();
    }

    @PutMapping @Transactional
    public ResponseEntity atualizarProdutos(@RequestBody @Valid DadosDetalhamentoClientes dados){
        var clientes = repository.getReferenceById(dados.idCliente());
        clientes.atualizarCaracteristicas(dados);

        return ResponseEntity.ok(new DadosDetalhamentoClientes(clientes));
    }


}
