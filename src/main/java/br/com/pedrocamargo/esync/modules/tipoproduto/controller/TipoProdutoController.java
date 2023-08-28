package br.com.pedrocamargo.esync.modules.tipoproduto.controller;

import br.com.pedrocamargo.esync.modules.tipoproduto.dto.TipoProdutoDTO;
import br.com.pedrocamargo.esync.modules.tipoproduto.dto.TipoProdutoDTORequest;
import br.com.pedrocamargo.esync.modules.tipoproduto.model.TipoProduto;
import br.com.pedrocamargo.esync.modules.tipoproduto.repository.TipoProdutoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("tipoproduto")
public class TipoProdutoController {

    @Autowired
    TipoProdutoRepository repository;

    @GetMapping
    public ResponseEntity<Page<TipoProdutoDTO>> getTiposProduto(@PageableDefault(sort = "id") Pageable pageable){
        return ResponseEntity.ok(repository.findAll(pageable).map(TipoProdutoDTO::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoProdutoDTO> getTipoProdutoByid(@PathVariable("id") Long idTipoProduto){
        return ResponseEntity.ok(new TipoProdutoDTO(repository.getReferenceById(idTipoProduto)));
    }

    @PostMapping
    @Transactional
    public ResponseEntity addTipoProduto(@RequestBody @Valid TipoProdutoDTORequest tipoProdutoRequest, UriComponentsBuilder uriBuilder){
        TipoProduto tipoProduto = repository.save(new TipoProduto(tipoProdutoRequest.descricao()));

        URI uri = uriBuilder.path("tipoproduto/{id}").buildAndExpand(tipoProduto.getId()).toUri();
        return ResponseEntity.created(uri).body(new TipoProdutoDTO(tipoProduto));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity updateTipoProduto(@PathVariable("id") Long idTipoProduto, @RequestBody TipoProdutoDTORequest tipoProdutoRequest){
        TipoProduto tipoProduto = repository.getReferenceById(idTipoProduto);
        tipoProduto.update(tipoProdutoRequest);

        return ResponseEntity.ok(new TipoProdutoDTO(tipoProduto));
    }

}
