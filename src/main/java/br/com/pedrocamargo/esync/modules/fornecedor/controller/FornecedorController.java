package br.com.pedrocamargo.esync.modules.fornecedor.controller;

import br.com.pedrocamargo.esync.infra.helpers.MessageResponse;
import br.com.pedrocamargo.esync.modules.endereco.dto.EnderecoDTO;
import br.com.pedrocamargo.esync.modules.fornecedor.dto.FornecedorDTO;
import br.com.pedrocamargo.esync.modules.fornecedor.dto.FornecedorDTORequest;
import br.com.pedrocamargo.esync.modules.fornecedor.model.Fornecedor;
import br.com.pedrocamargo.esync.modules.fornecedor.service.FornecedorService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Fornecedor", description = "Realizar todas as operações relacionados com os fornecedores")
@RestController
@RequestMapping("/v1/fornecedor")
@SecurityRequirement(name = "bearer-key")
public class FornecedorController {

    @Autowired
    FornecedorService fornecedorService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna uma lista com todos os fornecedores ordenados por ID"),
            @ApiResponse(responseCode = "401",description = "Falha ao se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "403",description = "Necessario se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
    })
    @GetMapping
    public ResponseEntity<Page<FornecedorDTO>> getFornecedores(Pageable pageable){
        Page<FornecedorDTO> page = fornecedorService.getFornecedores(pageable);
        return ResponseEntity.ok(page);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Retorna o fornecedor com o ID informado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EnderecoDTO.class))),
            @ApiResponse(responseCode = "401",description = "Falha ao se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "403",description = "Necessario se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "404",description = "Não existe fornecedor com o ID informado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
    })
    @GetMapping("/{id}")
    public ResponseEntity getFornecedorById(@PathVariable("id") Long idFornecedor){
        return ResponseEntity.ok(fornecedorService.getFornecedor(idFornecedor));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "Fornecedor cadastrado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EnderecoDTO.class))),
            @ApiResponse(responseCode = "400",description = "Dados enviados de forma incorreta", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "401",description = "Falha ao se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "403",description = "Necessario se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
    })
    @PostMapping
    @Transactional
    public ResponseEntity addFornecedor(@RequestBody @Valid FornecedorDTORequest fornecedorRequest, UriComponentsBuilder uriBuilder){
        Fornecedor fornecedorInserido = fornecedorService.adicionarFornecedor(fornecedorRequest);
        URI uri = uriBuilder.path("fornecedor/{id}").buildAndExpand(fornecedorInserido.getId()).toUri();
        return ResponseEntity.created(uri).body(new FornecedorDTO(fornecedorInserido));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Fornecedor alterado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EnderecoDTO.class))),
            @ApiResponse(responseCode = "400",description = "Dados enviados de forma incorreta", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "401",description = "Falha ao se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "403",description = "Necessario se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "404",description = "Não existe fornecedor com o ID informado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
    })
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity updateFornecedor(@PathVariable("id") Long idFornecedor, @RequestBody FornecedorDTORequest fornecedorRequest){
        Fornecedor fornecedor = fornecedorService.atualizarFornecedor(idFornecedor,fornecedorRequest);
        return ResponseEntity.ok(new FornecedorDTO(fornecedor));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Fornecedor excluído com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EnderecoDTO.class))),
            @ApiResponse(responseCode = "401",description = "Falha ao se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "403",description = "Necessario se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "404",description = "Não existe fornecedor com o ID informado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity deleteFornecedor(@PathVariable("id") Long idFornecedor){
        fornecedorService.deletarFornecedor(idFornecedor);
        return ResponseEntity.ok().body(new MessageResponse(HttpStatus.OK.value(), "Fornecedor com ID " + idFornecedor + " excluiído com sucesso."));
    }

}
