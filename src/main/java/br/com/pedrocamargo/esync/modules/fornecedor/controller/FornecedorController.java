package br.com.pedrocamargo.esync.modules.fornecedor.controller;

import br.com.pedrocamargo.esync.helpers.MessageResponse;
import br.com.pedrocamargo.esync.modules.fornecedor.dto.FornecedorDTO;
import br.com.pedrocamargo.esync.modules.fornecedor.dto.FornecedorDTORequest;
import br.com.pedrocamargo.esync.modules.fornecedor.model.Fornecedor;
import br.com.pedrocamargo.esync.modules.fornecedor.repository.FornecedorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(name = "fornecedor")
public class FornecedorController {

    @Autowired
    FornecedorRepository repository;

    @GetMapping
    public ResponseEntity<Page<FornecedorDTO>> getFornecedores(Pageable pageable){
        Page<FornecedorDTO> page = repository.findAll(pageable).map(FornecedorDTO::new);

        return ResponseEntity.ok(page);
    }

    @GetMapping("{id}")
    public ResponseEntity getFornecedorById(@PathVariable("id") Long idFornecedor){
        if(idFornecedor == null){
            return ResponseEntity.badRequest().body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "O ID nao pode ser nulo."));
        }
        return ResponseEntity.ok(repository.getReferenceById(idFornecedor));
    }

    @PostMapping
    @Transactional
    public ResponseEntity addFornecedor(@RequestBody FornecedorDTORequest fornecedorRequest, UriComponentsBuilder uriBuilder){
        Fornecedor fornecedorInserido = repository.save(new Fornecedor(fornecedorRequest));
        URI uri = uriBuilder.path("fornecedor/{id}").buildAndExpand(fornecedorInserido.getId()).toUri();

        return ResponseEntity.created(uri).body(new FornecedorDTO(fornecedorInserido));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity updateFornecedor(@PathVariable("id") Long idFornecedor, @RequestBody FornecedorDTORequest fornecedorRequest){
        if(idFornecedor == null){
            return ResponseEntity.badRequest().body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "O ID nao pode ser nulo."));
        }
        Fornecedor fornecedor = repository.getReferenceById(idFornecedor);
        fornecedor.update(fornecedorRequest);

        return ResponseEntity.ok(new FornecedorDTO(fornecedor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteFornecedor(@PathVariable("id") Long idFornecedor){
        if(idFornecedor == null){
            return ResponseEntity.badRequest().body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "O ID nao pode ser nulo."));
        }
        Fornecedor fornecedor = repository.getReferenceById(idFornecedor);
        fornecedor.delete();

        return ResponseEntity.ok().body(new MessageResponse(HttpStatus.OK.value(), "Fornecedor com ID " + idFornecedor + " excluiído com sucesso."));
    }

}
