package br.com.pedrocamargo.esync.modules.autenticacao.controller;

import br.com.pedrocamargo.esync.infra.helpers.MessageResponse;
import br.com.pedrocamargo.esync.modules.autenticacao.dto.AutenticacaoDTO;
import br.com.pedrocamargo.esync.modules.usuario.model.Usuario;
import br.com.pedrocamargo.esync.infra.service.TokenService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Autenticação", description = "Rota para realizar a autenticação do usuário")
@RestController
@RequestMapping("/v1/auth")
public class AutenticacaoController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenService tokenService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autenticação realizada com sucesso", content = @Content(mediaType = "application/json",schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "400",description = "Dados enviados incorretamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "401",description = "Falha ao se autenticar no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
    })
    @PostMapping
    public ResponseEntity auth(@RequestBody @Valid AutenticacaoDTO autenticacaoDTO){
        Authentication authentication = new UsernamePasswordAuthenticationToken(autenticacaoDTO.usuario(),autenticacaoDTO.senha());
        Authentication auth = authenticationManager.authenticate(authentication);
        String jwtToken = tokenService.generateToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(new MessageResponse(HttpStatus.OK.value(), jwtToken));
    }

}
