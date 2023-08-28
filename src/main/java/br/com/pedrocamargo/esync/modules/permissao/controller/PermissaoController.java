package br.com.pedrocamargo.esync.modules.permissao.controller;

import br.com.pedrocamargo.esync.modules.permissao.dto.PermissaoDTO;
import br.com.pedrocamargo.esync.modules.permissao.dto.PermissaoDTORequest;
import br.com.pedrocamargo.esync.modules.permissao.model.Permissao;
import br.com.pedrocamargo.esync.modules.permissao.repository.PermissaoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("permissao")
public class PermissaoController {

    @Autowired
    PermissaoRepository repository;

    @GetMapping
    public ResponseEntity<List<PermissaoDTO>> getPermissoes(){
        return ResponseEntity.ok().body(repository.findAll().stream().map(PermissaoDTO::new).toList());
    }

    @PostMapping
    @Transactional
    public ResponseEntity addPermissao(@RequestBody @Valid PermissaoDTORequest permissaoRequest, UriComponentsBuilder uriBuilder){
        Permissao permissao = repository.save(new Permissao(permissaoRequest.descricao()));

        URI uri = uriBuilder.path("permissao/{id}").buildAndExpand(permissao.getId()).toUri();
        return ResponseEntity.created(uri).body(new PermissaoDTO(permissao));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity updatePermissao(@PathVariable("id") Long idPermissao, @RequestBody PermissaoDTORequest permissaoRequest){
        Permissao permissao = repository.getReferenceById(idPermissao);
        permissao.update(permissaoRequest);

        return ResponseEntity.ok(permissao);
    }

}
