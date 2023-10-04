package br.com.pedrocamargo.esync.modules.endereco.controller;

import br.com.pedrocamargo.esync.infra.helpers.MessageResponse;
import br.com.pedrocamargo.esync.modules.endereco.dto.EnderecoDTO;
import br.com.pedrocamargo.esync.modules.endereco.dto.EnderecoDTORequest;
import br.com.pedrocamargo.esync.modules.endereco.model.Endereco;
import br.com.pedrocamargo.esync.modules.endereco.repository.EnderecoRepository;
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
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Tag(name = "Endereço", description = "Realizar todas as operações relacionados com os endereços")
@RestController
@RequestMapping("/v1/endereco")
@SecurityRequirement(name = "bearer-key")
public class EnderecoController {

    @Autowired
    EnderecoRepository repository;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna uma lista com todos os endereços ordenados por ID"),
            @ApiResponse(responseCode = "401",description = "Falha ao se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "403",description = "Necessario se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
    })
    @GetMapping
    public ResponseEntity<Page<EnderecoDTO>> getAllEnderecos(@PageableDefault(sort = "id") Pageable pageable){
        Page<EnderecoDTO> page = repository.findAll(pageable).map(EnderecoDTO::new);

        return ResponseEntity.ok(page);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Retorna o endereço com o ID informado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EnderecoDTO.class))),
            @ApiResponse(responseCode = "401",description = "Falha ao se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "403",description = "Necessario se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "404",description = "Não existe endereço com o ID informado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
    })
    @GetMapping("/{id}")
    public ResponseEntity<EnderecoDTO> getEnderecoById(@PathVariable("id") Long idEndereco){
        return ResponseEntity.ok(new EnderecoDTO(repository.getReferenceById(idEndereco)));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "Endereço cadastrado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EnderecoDTO.class))),
            @ApiResponse(responseCode = "400",description = "Dados enviados de forma incorreta", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "401",description = "Falha ao se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "403",description = "Necessario se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
    })
    @PostMapping
    @Transactional
    public ResponseEntity<EnderecoDTO> addEndereco(@RequestBody @Valid EnderecoDTORequest enderecoRequest, UriComponentsBuilder uriBuilder){
        Endereco enderecoInserido = repository.save(new Endereco(enderecoRequest));
        URI uri = uriBuilder.path("endereco/{id}").buildAndExpand(enderecoInserido.getId()).toUri();
        return ResponseEntity.created(uri).body(new EnderecoDTO(enderecoInserido));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Endereço alterado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EnderecoDTO.class))),
            @ApiResponse(responseCode = "400",description = "Dados enviados de forma incorreta", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "401",description = "Falha ao se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "403",description = "Necessario se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "404",description = "Não existe endereço com o ID informado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
    })
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity updateEndereco(@PathVariable("id") Long idEndereco, @RequestBody EnderecoDTORequest enderecoRequest){
        Endereco endereco = repository.getReferenceById(idEndereco);
        endereco.update(enderecoRequest);
        return ResponseEntity.ok(endereco);
    }

}
