package br.com.pedrocamargo.esync.modules.locacao.controller;

import br.com.pedrocamargo.esync.modules.locacao.dto.LocacaoDTORequest;
import br.com.pedrocamargo.esync.modules.locacao.dto.LocacaoDTOUpdate;
import br.com.pedrocamargo.esync.modules.locacao.model.Locacao;
import br.com.pedrocamargo.esync.modules.locacao.repository.LocacaoRepository;
import br.com.pedrocamargo.esync.modules.loja.model.Loja;
import br.com.pedrocamargo.esync.modules.loja.repository.LojaRepository;
import br.com.pedrocamargo.esync.modules.produto.model.Produto;
import br.com.pedrocamargo.esync.modules.produto.repository.ProdutoRepository;
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
@RequestMapping("locacao")
public class LocacaoController {


    @Autowired
    private LocacaoRepository repository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private LojaRepository lojaRepository;

    @GetMapping
    public ResponseEntity<Page<Locacao>> getLocacoes(@PageableDefault(sort = "id")Pageable pageable){
        return ResponseEntity.ok(repository.findAll(pageable));
    }

    @GetMapping("/{idProduto}")
    public ResponseEntity<Locacao> getLocacao(@PathVariable(name = "idProduto") Long idProduto){
        return ResponseEntity.ok(repository.getReferenceByIdProduto(idProduto));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Locacao> addLocacao(@RequestBody @Valid LocacaoDTORequest locacaoRequest, UriComponentsBuilder uriBuilder){
        Produto produto = produtoRepository.getReferenceById(locacaoRequest.idProduto());
        Loja loja = lojaRepository.getReferenceById(locacaoRequest.idLoja());
        Locacao locacao = repository.save(new Locacao(null,produto,loja));

        URI uri = uriBuilder.path("/locacao/{id}").buildAndExpand(locacao.getId()).toUri();
        return ResponseEntity.created(uri).body(locacao);
    }

    @PutMapping("/{idProduto}")
    @Transactional
    public ResponseEntity<Locacao> updateLocacao(@PathVariable Long idProduto, @RequestBody @Valid LocacaoDTOUpdate locacaoRequest){
        Loja loja = lojaRepository.getReferenceById(locacaoRequest.idLoja());
        Locacao locacao = repository.getReferenceByIdProduto(idProduto);
        locacao.update(loja);

        return ResponseEntity.ok(locacao);
    }
}
