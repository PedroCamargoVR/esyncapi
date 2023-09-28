package br.com.pedrocamargo.esync.modules.usuario.controller;

import br.com.pedrocamargo.esync.helpers.MessageResponse;
import br.com.pedrocamargo.esync.modules.permissao.model.Permissao;
import br.com.pedrocamargo.esync.modules.permissao.repository.PermissaoRepository;
import br.com.pedrocamargo.esync.modules.usuario.dto.UsuarioDTO;
import br.com.pedrocamargo.esync.modules.usuario.dto.UsuarioDTORequest;
import br.com.pedrocamargo.esync.modules.usuario.model.Usuario;
import br.com.pedrocamargo.esync.modules.usuario.repository.UsuarioRepository;
import br.com.pedrocamargo.esync.modules.usuario.service.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
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

@RestController
@RequestMapping("usuario")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<Page<UsuarioDTO>> getAllUsuarios(@PageableDefault(sort = "nome") Pageable pageable){
        Page<UsuarioDTO> page = usuarioService.getUsuarios(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getUsuarioById(@PathVariable("id") Long idUsuario){
        UsuarioDTO usuarioDTO = new UsuarioDTO(usuarioService.getUsuario(idUsuario));
        return ResponseEntity.ok(usuarioDTO);
    }

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

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity updateUsuario(@PathVariable("id") Long idUsuario, @RequestBody UsuarioDTORequest usuarioRequest){
        Usuario usuario = usuarioService.atualizarUsuario(idUsuario,usuarioRequest);
        return ResponseEntity.ok().body(new UsuarioDTO(usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUsuario(@PathVariable("id") Long idUsuario){
        usuarioService.deletarUsuario(idUsuario);
        return ResponseEntity.ok().body(new MessageResponse(HttpStatus.OK.value(), "Usuario com ID " + idUsuario + " foi excluído do sistema."));
    }
}
