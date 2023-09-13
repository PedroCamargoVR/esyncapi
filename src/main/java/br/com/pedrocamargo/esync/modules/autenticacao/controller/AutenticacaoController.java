package br.com.pedrocamargo.esync.modules.autenticacao.controller;

import br.com.pedrocamargo.esync.helpers.MessageResponse;
import br.com.pedrocamargo.esync.modules.autenticacao.dto.AutenticacaoDTO;
import br.com.pedrocamargo.esync.modules.usuario.model.Usuario;
import br.com.pedrocamargo.esync.service.TokenService;
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

@RestController
@RequestMapping("auth")
public class AutenticacaoController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenService tokenService;

    @PostMapping
    public ResponseEntity auth(@RequestBody @Valid AutenticacaoDTO autenticacaoDTO){
        Authentication authentication = new UsernamePasswordAuthenticationToken(autenticacaoDTO.usuario(),autenticacaoDTO.senha());
        Authentication auth = authenticationManager.authenticate(authentication);
        String jwtToken = tokenService.generateToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(new MessageResponse(HttpStatus.OK.value(), jwtToken));
    }

}
