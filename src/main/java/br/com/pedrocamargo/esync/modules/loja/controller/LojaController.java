package br.com.pedrocamargo.esync.modules.loja.controller;

import br.com.pedrocamargo.esync.helpers.MessageResponse;
import br.com.pedrocamargo.esync.modules.endereco.model.Endereco;
import br.com.pedrocamargo.esync.modules.endereco.repository.EnderecoRepository;
import br.com.pedrocamargo.esync.modules.loja.dto.LojaDTO;
import br.com.pedrocamargo.esync.modules.loja.dto.LojaDTORequest;
import br.com.pedrocamargo.esync.modules.loja.model.Loja;
import br.com.pedrocamargo.esync.modules.loja.repository.LojaRepository;
import br.com.pedrocamargo.esync.modules.loja.service.LojaService;
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
@RequestMapping("loja")
public class LojaController {

    @Autowired
    LojaService lojaService;

    @GetMapping
    public ResponseEntity<Page<LojaDTO>> getLojas(Pageable pageable){
        Page<LojaDTO> page = lojaService.getLojas(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity getLojaById(@PathVariable("id") Long idLoja){
        return ResponseEntity.ok(lojaService.getLoja(idLoja));
    }

    @PostMapping
    @Transactional
    public ResponseEntity addLoja(@RequestBody @Valid LojaDTORequest lojaRequest, UriComponentsBuilder uriBuilder){
        Loja lojaInserida = lojaService.adicionarLoja(lojaRequest);
        URI uri = uriBuilder.path("loja/{id}").buildAndExpand(lojaInserida.getId()).toUri();
        return ResponseEntity.created(uri).body(new LojaDTO(lojaInserida));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity updateLoja(@PathVariable("id") Long idLoja, @RequestBody LojaDTORequest lojaRequest){
        Loja loja = lojaService.atualizarLoja(idLoja,lojaRequest);
        return ResponseEntity.ok(new LojaDTO(loja));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteLoja(@PathVariable("id") Long idLoja){
        lojaService.deletarLoja(idLoja);
        return ResponseEntity.ok().body(new MessageResponse(HttpStatus.OK.value(),"A Loja com o ID " + idLoja + " foi excluida corretamente."));
    }

}
