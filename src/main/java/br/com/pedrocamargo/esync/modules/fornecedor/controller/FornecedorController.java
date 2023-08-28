package br.com.pedrocamargo.esync.modules.fornecedor.controller;

import br.com.pedrocamargo.esync.helpers.MessageResponse;
import br.com.pedrocamargo.esync.modules.endereco.model.Endereco;
import br.com.pedrocamargo.esync.modules.endereco.repository.EnderecoRepository;
import br.com.pedrocamargo.esync.modules.fornecedor.dto.FornecedorDTO;
import br.com.pedrocamargo.esync.modules.fornecedor.dto.FornecedorDTORequest;
import br.com.pedrocamargo.esync.modules.fornecedor.model.Fornecedor;
import br.com.pedrocamargo.esync.modules.fornecedor.repository.FornecedorRepository;
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
@RequestMapping(name = "fornecedor")
public class FornecedorController {

    @Autowired
    FornecedorRepository repository;

    @Autowired
    EnderecoRepository enderecoRepository;

    @GetMapping
    public ResponseEntity<Page<FornecedorDTO>> getFornecedores(Pageable pageable){
        Page<FornecedorDTO> page = repository.findAll(pageable).map(FornecedorDTO::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity getFornecedorById(@PathVariable("id") Long idFornecedor){
        return ResponseEntity.ok(repository.getReferenceById(idFornecedor));
    }

    @PostMapping
    @Transactional
    public ResponseEntity addFornecedor(@RequestBody @Valid FornecedorDTORequest fornecedorRequest, UriComponentsBuilder uriBuilder){
        Endereco endereco = enderecoRepository.getReferenceById(fornecedorRequest.id_endereco());
        Fornecedor fornecedorInserido = repository.save(new Fornecedor(endereco,fornecedorRequest));
        URI uri = uriBuilder.path("fornecedor/{id}").buildAndExpand(fornecedorInserido.getId()).toUri();

        return ResponseEntity.created(uri).body(new FornecedorDTO(fornecedorInserido));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity updateFornecedor(@PathVariable("id") Long idFornecedor, @RequestBody FornecedorDTORequest fornecedorRequest){
        Fornecedor fornecedor = repository.getReferenceById(idFornecedor);
        Endereco endereco = fornecedor.getEndereco();
        if(fornecedor.getEndereco().getId() != fornecedorRequest.id_endereco()){
            endereco = enderecoRepository.getReferenceById(fornecedorRequest.id_endereco());
        }
        fornecedor.update(endereco,fornecedorRequest);

        return ResponseEntity.ok(new FornecedorDTO(fornecedor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteFornecedor(@PathVariable("id") Long idFornecedor){
        Fornecedor fornecedor = repository.getReferenceById(idFornecedor);
        fornecedor.delete();

        return ResponseEntity.ok().body(new MessageResponse(HttpStatus.OK.value(), "Fornecedor com ID " + idFornecedor + " excluiído com sucesso."));
    }

}
