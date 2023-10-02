package br.com.pedrocamargo.esync.modules.fornecedor.controller;

import br.com.pedrocamargo.esync.infra.helpers.MessageResponse;
import br.com.pedrocamargo.esync.modules.fornecedor.dto.FornecedorDTO;
import br.com.pedrocamargo.esync.modules.fornecedor.dto.FornecedorDTORequest;
import br.com.pedrocamargo.esync.modules.fornecedor.model.Fornecedor;
import br.com.pedrocamargo.esync.modules.fornecedor.service.FornecedorService;
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
@RequestMapping("fornecedor")
public class FornecedorController {

    @Autowired
    FornecedorService fornecedorService;

    @GetMapping
    public ResponseEntity<Page<FornecedorDTO>> getFornecedores(Pageable pageable){
        Page<FornecedorDTO> page = fornecedorService.getFornecedores(pageable);
        return ResponseEntity.ok(page);
    }

   @GetMapping("/{id}")
    public ResponseEntity getFornecedorById(@PathVariable("id") Long idFornecedor){
        return ResponseEntity.ok(fornecedorService.getFornecedor(idFornecedor));
    }

    @PostMapping
    @Transactional
    public ResponseEntity addFornecedor(@RequestBody @Valid FornecedorDTORequest fornecedorRequest, UriComponentsBuilder uriBuilder){
        Fornecedor fornecedorInserido = fornecedorService.adicionarFornecedor(fornecedorRequest);
        URI uri = uriBuilder.path("fornecedor/{id}").buildAndExpand(fornecedorInserido.getId()).toUri();
        return ResponseEntity.created(uri).body(new FornecedorDTO(fornecedorInserido));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity updateFornecedor(@PathVariable("id") Long idFornecedor, @RequestBody FornecedorDTORequest fornecedorRequest){
        Fornecedor fornecedor = fornecedorService.atualizarFornecedor(idFornecedor,fornecedorRequest);
        return ResponseEntity.ok(new FornecedorDTO(fornecedor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteFornecedor(@PathVariable("id") Long idFornecedor){
        fornecedorService.deletarFornecedor(idFornecedor);
        return ResponseEntity.ok().body(new MessageResponse(HttpStatus.OK.value(), "Fornecedor com ID " + idFornecedor + " excluiído com sucesso."));
    }

}
