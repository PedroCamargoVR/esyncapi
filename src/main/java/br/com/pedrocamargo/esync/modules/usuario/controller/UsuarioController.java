package br.com.pedrocamargo.esync.modules.usuario.controller;

import br.com.pedrocamargo.esync.infra.helpers.MessageResponse;
import br.com.pedrocamargo.esync.modules.endereco.dto.EnderecoDTO;
import br.com.pedrocamargo.esync.modules.usuario.dto.UsuarioDTO;
import br.com.pedrocamargo.esync.modules.usuario.dto.UsuarioDTORequest;
import br.com.pedrocamargo.esync.modules.usuario.model.Usuario;
import br.com.pedrocamargo.esync.modules.usuario.service.UsuarioService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Tag(name = "Usuario", description = "Realizar todas as operações relacionados com os usuários")
@RestController
@RequestMapping("/v1/usuario")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna uma lista com todos os usuários ordenados por ID"),
            @ApiResponse(responseCode = "401",description = "Falha ao se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "403",description = "Necessario se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
    })
    @GetMapping
    public ResponseEntity<Page<UsuarioDTO>> getAllUsuarios(@PageableDefault(sort = "nome") Pageable pageable){
        Page<UsuarioDTO> page = usuarioService.getUsuarios(pageable);
        return ResponseEntity.ok(page);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Retorna o usuário com o ID informado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EnderecoDTO.class))),
            @ApiResponse(responseCode = "401",description = "Falha ao se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "403",description = "Necessario se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "404",description = "Não existe usuário com o ID informado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
    })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getUsuarioById(@PathVariable("id") Long idUsuario){
        UsuarioDTO usuarioDTO = new UsuarioDTO(usuarioService.getUsuario(idUsuario));
        return ResponseEntity.ok(usuarioDTO);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "Usuário cadastrado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EnderecoDTO.class))),
            @ApiResponse(responseCode = "400",description = "Dados enviados de forma incorreta", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "401",description = "Falha ao se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "403",description = "Necessario se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
    })
    @PostMapping
    @Transactional
    public ResponseEntity addUsuario(@RequestBody @Valid UsuarioDTORequest usuarioRequest, UriComponentsBuilder uriBuilder){
        Usuario usuarioInserido = usuarioService.adicionarUsuario(usuarioRequest);
        if(usuarioInserido == null){
            return new ResponseEntity(new MessageResponse(HttpStatus.NOT_FOUND.value(), "Permissao informada nao existe"),HttpStatus.NOT_FOUND);
        }

        URI uri = uriBuilder.path("usuario/{id}").buildAndExpand(usuarioInserido.getId()).toUri();
        return ResponseEntity.created(uri).body(new UsuarioDTO(usuarioInserido));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Usuário alterado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EnderecoDTO.class))),
            @ApiResponse(responseCode = "400",description = "Dados enviados de forma incorreta", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "401",description = "Falha ao se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "403",description = "Necessario se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "404",description = "Não existe usuário com o ID informado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
    })
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity updateUsuario(@PathVariable("id") Long idUsuario, @RequestBody UsuarioDTORequest usuarioRequest){
        Usuario usuario = usuarioService.atualizarUsuario(idUsuario,usuarioRequest);
        return ResponseEntity.ok().body(new UsuarioDTO(usuario));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Usuário excluído com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EnderecoDTO.class))),
            @ApiResponse(responseCode = "401",description = "Falha ao se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "403",description = "Necessario se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "404",description = "Não existe usuário com o ID informado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity deleteUsuario(@PathVariable("id") Long idUsuario){
        usuarioService.deletarUsuario(idUsuario);
        return ResponseEntity.ok().body(new MessageResponse(HttpStatus.OK.value(), "Usuario com ID " + idUsuario + " foi excluído do sistema."));
    }
}
