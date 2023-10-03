package br.com.pedrocamargo.esync.modules.locacao.controller;

import br.com.pedrocamargo.esync.infra.helpers.MessageResponse;
import br.com.pedrocamargo.esync.modules.endereco.dto.EnderecoDTO;
import br.com.pedrocamargo.esync.modules.locacao.dto.LocacaoDTO;
import br.com.pedrocamargo.esync.modules.locacao.dto.LocacaoDTORequest;
import br.com.pedrocamargo.esync.modules.locacao.dto.LocacaoDTOUpdate;
import br.com.pedrocamargo.esync.modules.locacao.model.Locacao;
import br.com.pedrocamargo.esync.modules.locacao.repository.LocacaoRepository;
import br.com.pedrocamargo.esync.modules.locacao.service.LocacaoService;
import br.com.pedrocamargo.esync.modules.loja.model.Loja;
import br.com.pedrocamargo.esync.modules.loja.repository.LojaRepository;
import br.com.pedrocamargo.esync.modules.produto.model.Produto;
import br.com.pedrocamargo.esync.modules.produto.repository.ProdutoRepository;
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

@Tag(name = "Locação", description = "Realizar todas as operações relacionados com as locações")
@RestController
@RequestMapping("/v1/locacao")
public class LocacaoController {

    @Autowired
    LocacaoService locacaoService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna uma lista com todas as locações ordenadas por ID"),
            @ApiResponse(responseCode = "401",description = "Falha ao se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "403",description = "Necessario se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
    })
    @GetMapping
    public ResponseEntity<Page<LocacaoDTO>> getLocacoes(@PageableDefault(sort = "id")Pageable pageable){
        Page<LocacaoDTO> page = locacaoService.getLocacoes(pageable);
        return ResponseEntity.ok(page);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Retorna a locação com o ID do produto informado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EnderecoDTO.class))),
            @ApiResponse(responseCode = "401",description = "Falha ao se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "403",description = "Necessario se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "404",description = "Não existe locação com o ID do produto informado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
    })
    @GetMapping("/{idProduto}")
    public ResponseEntity<LocacaoDTO> getLocacao(@PathVariable(name = "idProduto") Long idProduto){
        return ResponseEntity.ok(locacaoService.getLocacao(idProduto));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "Locação cadastrada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EnderecoDTO.class))),
            @ApiResponse(responseCode = "400",description = "Dados enviados de forma incorreta", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "401",description = "Falha ao se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "403",description = "Necessario se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
    })
    @PostMapping
    @Transactional
    public ResponseEntity<LocacaoDTO> addLocacao(@RequestBody @Valid LocacaoDTORequest locacaoRequest, UriComponentsBuilder uriBuilder){
        Locacao locacao = locacaoService.adicionarLocacao(locacaoRequest);
        URI uri = uriBuilder.path("/locacao/{id}").buildAndExpand(locacao.getId()).toUri();
        return ResponseEntity.created(uri).body(new LocacaoDTO(locacao));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Locação alterada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EnderecoDTO.class))),
            @ApiResponse(responseCode = "400",description = "Dados enviados de forma incorreta", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "401",description = "Falha ao se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "403",description = "Necessario se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "404",description = "Não existe locação com o ID do produto informado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
    })
    @PutMapping("/{idProduto}")
    @Transactional
    public ResponseEntity<LocacaoDTO> updateLocacao(@PathVariable Long idProduto, @RequestBody @Valid LocacaoDTOUpdate locacaoRequest){
        Locacao locacao = locacaoService.atualizarLocacao(idProduto,locacaoRequest);
        return ResponseEntity.ok(new LocacaoDTO(locacao));
    }
}
