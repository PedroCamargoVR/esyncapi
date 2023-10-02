package br.com.pedrocamargo.esync.modules.comprador.controller;

import br.com.pedrocamargo.esync.infra.helpers.MessageResponse;
import br.com.pedrocamargo.esync.modules.comprador.dto.CompradorDTO;
import br.com.pedrocamargo.esync.modules.comprador.dto.CompradorDTORequest;
import br.com.pedrocamargo.esync.modules.comprador.model.Comprador;
import br.com.pedrocamargo.esync.modules.comprador.service.CompradorService;
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

@RestController
@RequestMapping("comprador")
public class CompradorController {

    @Autowired
    CompradorService compradorService;

    @GetMapping
    public ResponseEntity<Page<CompradorDTO>> getCompradores(Pageable pagination) {
        Page<CompradorDTO> page = compradorService.getCompradores(pagination);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{idComprador}")
    public ResponseEntity getCompradorById(@PathVariable("id") Long idComprador){
        Comprador comprador = compradorService.getComprador(idComprador);
        return ResponseEntity.ok(new CompradorDTO(comprador));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<CompradorDTO> addComprador(@RequestBody @Valid CompradorDTORequest comprador, UriComponentsBuilder uriBuilder){
        Comprador compradorInserido = compradorService.salvarComprador(comprador);
        URI uri = uriBuilder.path("comprador/{id}").buildAndExpand(compradorInserido.getId()).toUri();
        return ResponseEntity.created(uri).body(new CompradorDTO(compradorInserido));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity updateComprador(@PathVariable("id") Long idComprador, @RequestBody CompradorDTORequest comprador){
        Comprador compradorBanco = compradorService.atualizarComprador(idComprador,comprador);
        return ResponseEntity.ok(compradorBanco);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteComprador(@PathVariable("id") Long idComprador){
        compradorService.deletarComprador(idComprador);
        return ResponseEntity.ok().body(new MessageResponse(HttpStatus.OK.value(), "O comprador ID " + idComprador + " foi excluido com sucesso."));
    }

}

