package br.com.develcode.api.controller;


import br.com.develcode.api.produtos.DadosDetalhamentoProdutos;
import br.com.develcode.api.produtos.DadosProdutos;
import br.com.develcode.api.model.Produtos;
import br.com.develcode.api.repository.ProdutosRepository;
import br.com.develcode.api.produtos.DadosAtualizacaoProdutos;
import br.com.develcode.api.produtos.DadosListagemProdutos;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/produtos")
public class ProdutosController {

    @Autowired
    private ProdutosRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosProdutos dados, UriComponentsBuilder uriBuilder){
        var produtos = new Produtos(dados);
        repository.save(new Produtos(dados));

        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(produtos.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoProdutos(produtos));


    }

    @GetMapping
    public ResponseEntity <Page<DadosListagemProdutos>> listar(@PageableDefault(sort = {"valor"}) Pageable paginacao){
        var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemProdutos::new);
        return ResponseEntity.ok(page);

    }

    @PutMapping @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoProdutos dados){
        var produtos = repository.getReferenceById(dados.id());
        produtos.atualizarCaracteristicas(dados);
;
        return ResponseEntity.ok(new DadosDetalhamentoProdutos(produtos));
    }

    @DeleteMapping("/{id}") @Transactional
    public ResponseEntity excluir(@PathVariable Long id){
        var produtos = repository.getReferenceById((id));
        produtos.excluir();

        return ResponseEntity.noContent().build();
    }


}
