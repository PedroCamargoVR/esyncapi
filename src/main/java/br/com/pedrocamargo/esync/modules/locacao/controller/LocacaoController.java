package br.com.pedrocamargo.esync.modules.locacao.controller;

import br.com.pedrocamargo.esync.modules.locacao.dto.LocacaoDTO;
import br.com.pedrocamargo.esync.modules.locacao.dto.LocacaoDTORequest;
import br.com.pedrocamargo.esync.modules.locacao.dto.LocacaoDTOUpdate;
import br.com.pedrocamargo.esync.modules.locacao.model.Locacao;
import br.com.pedrocamargo.esync.modules.locacao.repository.LocacaoRepository;
import br.com.pedrocamargo.esync.modules.locacao.service.LocacaoService;
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
    LocacaoService locacaoService;

    @GetMapping
    public ResponseEntity<Page<LocacaoDTO>> getLocacoes(@PageableDefault(sort = "id")Pageable pageable){
        Page<LocacaoDTO> page = locacaoService.getLocacoes(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{idProduto}")
    public ResponseEntity<LocacaoDTO> getLocacao(@PathVariable(name = "idProduto") Long idProduto){
        return ResponseEntity.ok(locacaoService.getLocacao(idProduto));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<LocacaoDTO> addLocacao(@RequestBody @Valid LocacaoDTORequest locacaoRequest, UriComponentsBuilder uriBuilder){
        Locacao locacao = locacaoService.adicionarLocacao(locacaoRequest);
        URI uri = uriBuilder.path("/locacao/{id}").buildAndExpand(locacao.getId()).toUri();
        return ResponseEntity.created(uri).body(new LocacaoDTO(locacao));
    }

    @PutMapping("/{idProduto}")
    @Transactional
    public ResponseEntity<LocacaoDTO> updateLocacao(@PathVariable Long idProduto, @RequestBody @Valid LocacaoDTOUpdate locacaoRequest){
        Locacao locacao = locacaoService.atualizarLocacao(idProduto,locacaoRequest);
        return ResponseEntity.ok(new LocacaoDTO(locacao));
    }
}
