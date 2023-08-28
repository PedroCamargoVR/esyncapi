package br.com.pedrocamargo.esync.modules.comprador.controller;

import br.com.pedrocamargo.esync.helpers.MessageResponse;
import br.com.pedrocamargo.esync.modules.comprador.dto.CompradorDTO;
import br.com.pedrocamargo.esync.modules.comprador.dto.CompradorDTORequest;
import br.com.pedrocamargo.esync.modules.comprador.model.Comprador;
import br.com.pedrocamargo.esync.modules.comprador.repository.CompradorRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("comprador")
public class CompradorController {

    @Autowired
    CompradorRepository repository;

    @GetMapping
    public ResponseEntity<Page<CompradorDTO>> getCompradores(Pageable pagination, UriComponentsBuilder uriBuilder) {
        Page<CompradorDTO> page = repository.findAll(pagination).map(CompradorDTO::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{idComprador}")
    public ResponseEntity getCompradorById(@PathVariable("id") Long idComprador){
        Comprador comprador = repository.getReferenceById(idComprador);
        return ResponseEntity.ok(new CompradorDTO(comprador));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<CompradorDTO> addComprador(@RequestBody @Valid CompradorDTORequest comprador, UriComponentsBuilder uriBuilder){
        Comprador compradorInserido = repository.save(new Comprador(comprador));

        URI uri = uriBuilder.path("comprador/{id}").buildAndExpand(compradorInserido.getId()).toUri();

        return ResponseEntity.created(uri).body(new CompradorDTO(compradorInserido));

    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity updateComprador(@PathVariable("id") Long idComprador, @RequestBody CompradorDTORequest comprador){
        Comprador compradorBanco = repository.getReferenceById(idComprador);
        compradorBanco.update(comprador);

        return ResponseEntity.ok(compradorBanco);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteComprador(@PathVariable("id") Long idComprador){
        Comprador comprador = repository.getReferenceById(idComprador);
        comprador.delete();
        return ResponseEntity.ok().body(new MessageResponse(HttpStatus.OK.value(), "O comprador ID " + idComprador + " foi excluido com sucesso."));
    }

}

