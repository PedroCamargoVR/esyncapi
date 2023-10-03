package br.com.pedrocamargo.esync.modules.comprador.controller;

import br.com.pedrocamargo.esync.infra.helpers.MessageResponse;
import br.com.pedrocamargo.esync.modules.comprador.dto.CompradorDTO;
import br.com.pedrocamargo.esync.modules.comprador.dto.CompradorDTORequest;
import br.com.pedrocamargo.esync.modules.comprador.model.Comprador;
import br.com.pedrocamargo.esync.modules.comprador.service.CompradorService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

@Tag(name="Comprador", description = "Realizar todas as operações relacionados com os compradores")
@RestController
@RequestMapping("/v1/comprador")
public class CompradorController {

    @Autowired
    CompradorService compradorService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Retorna todos os compradores"),
            @ApiResponse(responseCode = "401",description = "Falha ao se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "403",description = "Necessario se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class)))
    })
    @GetMapping
    public ResponseEntity<Page<CompradorDTO>> getCompradores(Pageable pagination) {
        Page<CompradorDTO> page = compradorService.getCompradores(pagination);
        return ResponseEntity.ok(page);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna comprador referente ao ID informado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CompradorDTO.class))),
            @ApiResponse(responseCode = "401",description = "Falha ao se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "403",description = "Necessario se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "404", description = "Não foi encontrado o comprador com o ID informado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
    })
    @GetMapping("/{idComprador}")
    public ResponseEntity<CompradorDTO> getCompradorById(@PathVariable("id") Long idComprador){
        Comprador comprador = compradorService.getComprador(idComprador);
        return ResponseEntity.ok(new CompradorDTO(comprador));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comprador cadastrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CompradorDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados enviados de forma incorreta", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "401",description = "Falha ao se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "403",description = "Necessario se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class)))
    })
    @PostMapping
    @Transactional
    public ResponseEntity<CompradorDTO> addComprador(@RequestBody @Valid CompradorDTORequest comprador, UriComponentsBuilder uriBuilder){
        Comprador compradorInserido = compradorService.salvarComprador(comprador);
        URI uri = uriBuilder.path("comprador/{id}").buildAndExpand(compradorInserido.getId()).toUri();
        return ResponseEntity.created(uri).body(new CompradorDTO(compradorInserido));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comprador atualizado conforme ID informado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CompradorDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados enviados de forma incorreta", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "401",description = "Falha ao se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "403",description = "Necessario se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "404", description = "Não foi encontrado o comprador com o ID informado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
    })
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<CompradorDTO> updateComprador(@PathVariable("id") Long idComprador, @RequestBody CompradorDTORequest comprador){
        Comprador compradorBanco = compradorService.atualizarComprador(idComprador,comprador);
        return ResponseEntity.ok(new CompradorDTO(compradorBanco));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comprador excluído conforme ID informado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "401",description = "Falha ao se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "403",description = "Necessario se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "404", description = "Não foi encontrado o comprador com o ID informado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteComprador(@PathVariable("id") Long idComprador){
        compradorService.deletarComprador(idComprador);
        return ResponseEntity.ok().body(new MessageResponse(HttpStatus.OK.value(), "O comprador ID " + idComprador + " foi excluido com sucesso."));
    }

}