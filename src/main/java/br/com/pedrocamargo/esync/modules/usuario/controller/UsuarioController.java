package br.com.pedrocamargo.esync.modules.usuario.controller;

import br.com.pedrocamargo.esync.modules.permissao.repository.PermissaoRepository;
import br.com.pedrocamargo.esync.modules.usuario.dto.UsuarioDTO;
import br.com.pedrocamargo.esync.modules.usuario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PermissaoRepository permissaoRepository;

    @GetMapping
    public ResponseEntity<Page<UsuarioDTO>> getAllUsuarios(@PageableDefault(sort = "nome") Pageable pageable){
        Page<UsuarioDTO> page = repository.findAll(pageable).map(usuario -> new UsuarioDTO(permissaoRepository.getReferenceById(usuario.getIdPermissao()),usuario));

        return ResponseEntity.ok(page);
    }

}
