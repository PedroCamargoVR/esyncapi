package br.com.pedrocamargo.esync.modules.usuario.service;

import br.com.pedrocamargo.esync.modules.permissao.model.Permissao;
import br.com.pedrocamargo.esync.modules.permissao.repository.PermissaoRepository;
import br.com.pedrocamargo.esync.modules.usuario.dto.UsuarioDTO;
import br.com.pedrocamargo.esync.modules.usuario.dto.UsuarioDTORequest;
import br.com.pedrocamargo.esync.modules.usuario.model.Usuario;
import br.com.pedrocamargo.esync.modules.usuario.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PermissaoRepository permissaoRepository;

    public Page<UsuarioDTO> getUsuarios(Pageable pageable){
        Page<UsuarioDTO> page = repository.findAll(pageable).map(usuario -> new UsuarioDTO(usuario));
        return page;
    }

    public Usuario getUsuario(Long idUsuario){
        Usuario usuario = repository.getReferenceById(idUsuario);
        return usuario;
    }

    public Usuario adicionarUsuario(UsuarioDTORequest usuarioRequest){
        try{
            Permissao permissao = permissaoRepository.getReferenceById(usuarioRequest.id_permissao());
            Usuario usuarioInserido = repository.save(new Usuario(null,permissao,usuarioRequest.nome(), usuarioRequest.usuario(), usuarioRequest.senha(), true));
            return usuarioInserido;
        }catch (EntityNotFoundException ex){
            return null;
        }
    }

    public Usuario atualizarUsuario(Long idUsuario, UsuarioDTORequest usuarioRequest){
        Usuario usuario = repository.getReferenceById(idUsuario);
        Permissao permissao = usuario.getPermissao();
        if(usuarioRequest.id_permissao() != null && usuario.getIdPermissao() != usuarioRequest.id_permissao()){
            permissao = permissaoRepository.getReferenceById(usuarioRequest.id_permissao());
        }
        usuario.update(usuarioRequest,permissao);

        return usuario;
    }

    public void deletarUsuario(Long idUsuario){
        Usuario usuario = repository.getReferenceById(idUsuario);
        usuario.delete();
    }
}
