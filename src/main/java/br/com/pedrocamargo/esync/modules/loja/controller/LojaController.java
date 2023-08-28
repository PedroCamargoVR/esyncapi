package br.com.pedrocamargo.esync.modules.loja.controller;

import br.com.pedrocamargo.esync.helpers.MessageResponse;
import br.com.pedrocamargo.esync.modules.endereco.model.Endereco;
import br.com.pedrocamargo.esync.modules.endereco.repository.EnderecoRepository;
import br.com.pedrocamargo.esync.modules.loja.dto.LojaDTO;
import br.com.pedrocamargo.esync.modules.loja.dto.LojaDTORequest;
import br.com.pedrocamargo.esync.modules.loja.model.Loja;
import br.com.pedrocamargo.esync.modules.loja.repository.LojaRepository;
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
    private LojaRepository repository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @GetMapping
    public ResponseEntity<Page<LojaDTO>> getLojas(Pageable pageable){
        Page<LojaDTO> page = repository.findAll(pageable).map(LojaDTO::new);

        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity getLojaById(@PathVariable("id") Long idLoja){
        return ResponseEntity.ok(repository.getReferenceById(idLoja));
    }

    @PostMapping
    @Transactional
    public ResponseEntity addLoja(@RequestBody @Valid LojaDTORequest lojaRequest, UriComponentsBuilder uriBuilder){
        Endereco endereco = enderecoRepository.getReferenceById(lojaRequest.id_endereco());

        Loja lojaInserida = repository.save(new Loja(endereco,lojaRequest));
        URI uri = uriBuilder.path("loja/{id}").buildAndExpand(lojaInserida.getId()).toUri();

        return ResponseEntity.created(uri).body(new LojaDTO(lojaInserida));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity updateLoja(@PathVariable("id") Long idLoja, @RequestBody LojaDTORequest lojaRequest){
        Loja loja = repository.getReferenceById(idLoja);
        if(loja.getEndereco().getId() != lojaRequest.id_endereco()){
            Endereco endereco = enderecoRepository.getReferenceById(lojaRequest.id_endereco());
            loja.update(endereco, lojaRequest);
        }
        loja.update(lojaRequest);

        return ResponseEntity.ok(new LojaDTO(loja));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteLoja(@PathVariable("id") Long idLoja){
        Loja loja = repository.getReferenceById(idLoja);
        loja.delete();

        return ResponseEntity.ok().body(new MessageResponse(HttpStatus.OK.value(),"A Loja com o ID " + idLoja + " foi excluida corretamente."));
    }

}
