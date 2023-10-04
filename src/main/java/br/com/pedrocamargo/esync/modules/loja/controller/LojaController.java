package br.com.pedrocamargo.esync.modules.loja.controller;

import br.com.pedrocamargo.esync.infra.helpers.MessageResponse;
import br.com.pedrocamargo.esync.modules.endereco.dto.EnderecoDTO;
import br.com.pedrocamargo.esync.modules.loja.dto.LojaDTO;
import br.com.pedrocamargo.esync.modules.loja.dto.LojaDTORequest;
import br.com.pedrocamargo.esync.modules.loja.model.Loja;
import br.com.pedrocamargo.esync.modules.loja.service.LojaService;
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

@Tag(name = "Loja", description = "Realizar todas as operações relacionados com as lojas")
@RestController
@RequestMapping("/v1/loja")
@SecurityRequirement(name = "bearer-key")
public class LojaController {

    @Autowired
    LojaService lojaService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna uma lista com todos as lojas ordenadas por ID"),
            @ApiResponse(responseCode = "401",description = "Falha ao se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "403",description = "Necessario se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
    })
    @GetMapping
    public ResponseEntity<Page<LojaDTO>> getLojas(Pageable pageable){
        Page<LojaDTO> page = lojaService.getLojas(pageable);
        return ResponseEntity.ok(page);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Retorna a loja com o ID informado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EnderecoDTO.class))),
            @ApiResponse(responseCode = "401",description = "Falha ao se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "403",description = "Necessario se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "404",description = "Não existe loja com o ID informado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
    })
    @GetMapping("/{id}")
    public ResponseEntity getLojaById(@PathVariable("id") Long idLoja){
        return ResponseEntity.ok(lojaService.getLoja(idLoja));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "Loja cadastrada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EnderecoDTO.class))),
            @ApiResponse(responseCode = "400",description = "Dados enviados de forma incorreta", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "401",description = "Falha ao se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "403",description = "Necessario se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
    })
    @PostMapping
    @Transactional
    public ResponseEntity addLoja(@RequestBody @Valid LojaDTORequest lojaRequest, UriComponentsBuilder uriBuilder){
        Loja lojaInserida = lojaService.adicionarLoja(lojaRequest);
        URI uri = uriBuilder.path("loja/{id}").buildAndExpand(lojaInserida.getId()).toUri();
        return ResponseEntity.created(uri).body(new LojaDTO(lojaInserida));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Loja alterada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EnderecoDTO.class))),
            @ApiResponse(responseCode = "400",description = "Dados enviados de forma incorreta", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "401",description = "Falha ao se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "403",description = "Necessario se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "404",description = "Não existe loja com o ID informado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
    })
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity updateLoja(@PathVariable("id") Long idLoja, @RequestBody LojaDTORequest lojaRequest){
        Loja loja = lojaService.atualizarLoja(idLoja,lojaRequest);
        return ResponseEntity.ok(new LojaDTO(loja));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Loja excluída com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EnderecoDTO.class))),
            @ApiResponse(responseCode = "401",description = "Falha ao se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "403",description = "Necessario se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "404",description = "Não existe loja com o ID informado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity deleteLoja(@PathVariable("id") Long idLoja){
        lojaService.deletarLoja(idLoja);
        return ResponseEntity.ok().body(new MessageResponse(HttpStatus.OK.value(),"A Loja com o ID " + idLoja + " foi excluida corretamente."));
    }

}
