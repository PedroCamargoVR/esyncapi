package br.com.pedrocamargo.esync.modules.tipoproduto.controller;

import br.com.pedrocamargo.esync.infra.helpers.MessageResponse;
import br.com.pedrocamargo.esync.modules.endereco.dto.EnderecoDTO;
import br.com.pedrocamargo.esync.modules.tipoproduto.dto.TipoProdutoDTO;
import br.com.pedrocamargo.esync.modules.tipoproduto.dto.TipoProdutoDTORequest;
import br.com.pedrocamargo.esync.modules.tipoproduto.model.TipoProduto;
import br.com.pedrocamargo.esync.modules.tipoproduto.repository.TipoProdutoRepository;
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
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Tag(name = "Tipo Produto", description = "Realizar todas as operações relacionados com os tipos de produtos")
@RestController
@RequestMapping("/v1/tipoproduto")
public class TipoProdutoController {

    @Autowired
    TipoProdutoRepository repository;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna uma lista com todos os tipos de produtos ordenados por ID"),
            @ApiResponse(responseCode = "401",description = "Falha ao se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "403",description = "Necessario se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
    })
    @GetMapping
    public ResponseEntity<Page<TipoProdutoDTO>> getTiposProduto(@PageableDefault(sort = "id") Pageable pageable){
        return ResponseEntity.ok(repository.findAll(pageable).map(TipoProdutoDTO::new));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Retorna o tipo de produto com o ID informado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EnderecoDTO.class))),
            @ApiResponse(responseCode = "401",description = "Falha ao se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "403",description = "Necessario se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "404",description = "Não existe tipo de produto com o ID informado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
    })
    @GetMapping("/{id}")
    public ResponseEntity<TipoProdutoDTO> getTipoProdutoByid(@PathVariable("id") Long idTipoProduto){
        return ResponseEntity.ok(new TipoProdutoDTO(repository.getReferenceById(idTipoProduto)));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "Tipo de produto cadastrado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EnderecoDTO.class))),
            @ApiResponse(responseCode = "400",description = "Dados enviados de forma incorreta", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "401",description = "Falha ao se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "403",description = "Necessario se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
    })
    @PostMapping
    @Transactional
    public ResponseEntity addTipoProduto(@RequestBody @Valid TipoProdutoDTORequest tipoProdutoRequest, UriComponentsBuilder uriBuilder){
        TipoProduto tipoProduto = repository.save(new TipoProduto(tipoProdutoRequest.descricao()));

        URI uri = uriBuilder.path("tipoproduto/{id}").buildAndExpand(tipoProduto.getId()).toUri();
        return ResponseEntity.created(uri).body(new TipoProdutoDTO(tipoProduto));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Tipo de produto alterado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EnderecoDTO.class))),
            @ApiResponse(responseCode = "400",description = "Dados enviados de forma incorreta", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "401",description = "Falha ao se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "403",description = "Necessario se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "404",description = "Não existe tipo de produto com o ID informado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
    })
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity updateTipoProduto(@PathVariable("id") Long idTipoProduto, @RequestBody TipoProdutoDTORequest tipoProdutoRequest){
        TipoProduto tipoProduto = repository.getReferenceById(idTipoProduto);
        tipoProduto.update(tipoProdutoRequest);

        return ResponseEntity.ok(new TipoProdutoDTO(tipoProduto));
    }

}
