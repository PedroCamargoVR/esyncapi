package br.com.pedrocamargo.esync.modules.usuario.controller;

import br.com.pedrocamargo.esync.helpers.MessageResponse;
import br.com.pedrocamargo.esync.modules.permissao.model.Permissao;
import br.com.pedrocamargo.esync.modules.permissao.repository.PermissaoRepository;
import br.com.pedrocamargo.esync.modules.usuario.dto.UsuarioDTO;
import br.com.pedrocamargo.esync.modules.usuario.dto.UsuarioDTORequest;
import br.com.pedrocamargo.esync.modules.usuario.model.Usuario;
import br.com.pedrocamargo.esync.modules.usuario.repository.UsuarioRepository;
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
    private UsuarioRepository repository;

    @Autowired
    private PermissaoRepository permissaoRepository;

    @GetMapping
    public ResponseEntity<Page<UsuarioDTO>> getAllUsuarios(@PageableDefault(sort = "nome") Pageable pageable){
        Page<UsuarioDTO> page = repository.findAll(pageable).map(usuario -> new UsuarioDTO(usuario));

        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getUsuarioById(@PathVariable("id") Long idUsuario){
        return ResponseEntity.ok(new UsuarioDTO(repository.getReferenceById(idUsuario)));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<UsuarioDTO> addUsuario(@RequestBody @Valid UsuarioDTORequest usuarioRequest, UriComponentsBuilder uriBuilder){
        try{
            Permissao permissao = permissaoRepository.getReferenceById(usuarioRequest.id_permissao());
            Usuario usuarioInserido = repository.save(new Usuario(null,permissao,usuarioRequest.nome(), usuarioRequest.usuario(), usuarioRequest.senha(), true));

            URI uri = uriBuilder.path("usuario/{id}").buildAndExpand(usuarioInserido.getId()).toUri();

            return ResponseEntity.created(uri).body(new UsuarioDTO(usuarioInserido));
        }catch(EntityNotFoundException ex){
            return new ResponseEntity(new MessageResponse(HttpStatus.NOT_FOUND.value(), "Permissao informada nao existe"),HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity updateUsuario(@PathVariable("id") Long idUsuario, @RequestBody UsuarioDTORequest usuarioRequest){
        Usuario usuario = repository.getReferenceById(idUsuario);
        Permissao permissao = usuario.getPermissao();
        if(usuarioRequest.id_permissao() != null && usuario.getIdPermissao() != usuarioRequest.id_permissao()){
            permissao = permissaoRepository.getReferenceById(usuarioRequest.id_permissao());
        }
        usuario.update(usuarioRequest,permissao);

        return ResponseEntity.ok().body(new UsuarioDTO(usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUsuario(@PathVariable("id") Long idUsuario){
        Usuario usuario = repository.getReferenceById(idUsuario);
        usuario.delete();

        return ResponseEntity.ok().body(new MessageResponse(HttpStatus.OK.value(), "Usuario com ID " + idUsuario + " foi excluído do sistema."));
    }
}
