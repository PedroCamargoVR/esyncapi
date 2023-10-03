package br.com.pedrocamargo.esync.modules.permissao.controller;

import br.com.pedrocamargo.esync.infra.helpers.MessageResponse;
import br.com.pedrocamargo.esync.modules.endereco.dto.EnderecoDTO;
import br.com.pedrocamargo.esync.modules.permissao.dto.PermissaoDTO;
import br.com.pedrocamargo.esync.modules.permissao.dto.PermissaoDTORequest;
import br.com.pedrocamargo.esync.modules.permissao.model.Permissao;
import br.com.pedrocamargo.esync.modules.permissao.repository.PermissaoRepository;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Tag(name = "Permissão", description = "Realizar todas as operações relacionados com as permissões")
@RestController
@RequestMapping("/v1/permissao")
public class PermissaoController {

    @Autowired
    PermissaoRepository repository;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna uma lista com todos as permissões ordenadas por ID"),
            @ApiResponse(responseCode = "401",description = "Falha ao se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "403",description = "Necessario se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
    })
    @GetMapping
    public ResponseEntity<List<PermissaoDTO>> getPermissoes(){
        return ResponseEntity.ok().body(repository.findAll().stream().map(PermissaoDTO::new).toList());
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "Permissão cadastrada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EnderecoDTO.class))),
            @ApiResponse(responseCode = "400",description = "Dados enviados de forma incorreta", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "401",description = "Falha ao se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "403",description = "Necessario se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
    })
    @PostMapping
    @Transactional
    public ResponseEntity addPermissao(@RequestBody @Valid PermissaoDTORequest permissaoRequest, UriComponentsBuilder uriBuilder){
        Permissao permissao = repository.save(new Permissao(permissaoRequest.descricao()));

        URI uri = uriBuilder.path("permissao/{id}").buildAndExpand(permissao.getId()).toUri();
        return ResponseEntity.created(uri).body(new PermissaoDTO(permissao));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Permissão alterada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EnderecoDTO.class))),
            @ApiResponse(responseCode = "400",description = "Dados enviados de forma incorreta", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "401",description = "Falha ao se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "403",description = "Necessario se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "404",description = "Não existe permissão com o ID informado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
    })
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity updatePermissao(@PathVariable("id") Long idPermissao, @RequestBody PermissaoDTORequest permissaoRequest){
        Permissao permissao = repository.getReferenceById(idPermissao);
        permissao.update(permissaoRequest);

        return ResponseEntity.ok(permissao);
    }

}
