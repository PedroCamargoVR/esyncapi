package br.com.pedrocamargo.esync.modules.endereco.controller;

import br.com.pedrocamargo.esync.modules.endereco.dto.EnderecoDTO;
import br.com.pedrocamargo.esync.modules.endereco.dto.EnderecoDTORequest;
import br.com.pedrocamargo.esync.modules.endereco.model.Endereco;
import br.com.pedrocamargo.esync.modules.endereco.repository.EnderecoRepository;
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
@RequestMapping("endereco")
public class EnderecoController {

    @Autowired
    EnderecoRepository repository;

    @GetMapping
    public ResponseEntity<Page<EnderecoDTO>> getAllEnderecos(@PageableDefault(sort = "id") Pageable pageable){
        Page<EnderecoDTO> page = repository.findAll(pageable).map(EnderecoDTO::new);

        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnderecoDTO> getEnderecoById(@PathVariable("id") Long idEndereco){
        return ResponseEntity.ok(new EnderecoDTO(repository.getReferenceById(idEndereco)));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<EnderecoDTO> addEndereco(@RequestBody @Valid EnderecoDTORequest enderecoRequest, UriComponentsBuilder uriBuilder){
        Endereco enderecoInserido = repository.save(new Endereco(enderecoRequest));
        URI uri = uriBuilder.path("endereco/{id}").buildAndExpand(enderecoInserido.getId()).toUri();
        return ResponseEntity.created(uri).body(new EnderecoDTO(enderecoInserido));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity updateEndereco(@PathVariable("id") Long idEndereco, @RequestBody EnderecoDTORequest enderecoRequest){
        Endereco endereco = repository.getReferenceById(idEndereco);
        endereco.update(enderecoRequest);
        return ResponseEntity.ok(endereco);
    }

}
