package br.com.pedrocamargo.esync.modules.produto.controller;

import br.com.pedrocamargo.esync.infra.helpers.MessageResponse;
import br.com.pedrocamargo.esync.modules.produto.dto.ProdutoDTO;
import br.com.pedrocamargo.esync.modules.produto.dto.ProdutoDTORequest;
import br.com.pedrocamargo.esync.modules.produto.model.Produto;
import br.com.pedrocamargo.esync.modules.produto.service.ProdutoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("produto")
public class ProdutoController {

    @Autowired
    ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<Page<ProdutoDTO>> getAllProdutos(@PageableDefault(sort = "id", size = 20) Pageable pageable){
        Page<ProdutoDTO> page = produtoService.getProdutos(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> getProdutoById(@PathVariable("id") Long idProduto){
        Produto produto = produtoService.getProduto(idProduto);
        return ResponseEntity.ok(new ProdutoDTO(produto));
    }

    @PostMapping
    @Transactional
    public ResponseEntity addProduto(@RequestBody @Valid ProdutoDTORequest produtoRequest, UriComponentsBuilder uriBUilder){
        Produto produto = produtoService.salvarProduto(produtoRequest);
        URI uri = uriBUilder.path("produto/{id}").buildAndExpand(produto.getId()).toUri();
        return ResponseEntity.created(uri).body(new ProdutoDTO(produto));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity updateProduto(@PathVariable("id") Long idProduto, @RequestBody ProdutoDTORequest produtoDTORequest){
        Produto produto = produtoService.atualizarProduto(idProduto,produtoDTORequest);
        return ResponseEntity.ok(new ProdutoDTO(produto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduto(@PathVariable("id") Long idProduto){
        produtoService.deletarProduto(idProduto);
        return ResponseEntity.ok(new MessageResponse(HttpStatus.OK.value(),"O produto com ID " + idProduto + " foi excluido."));
    }
}
