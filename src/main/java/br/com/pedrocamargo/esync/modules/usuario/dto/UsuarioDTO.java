package br.com.pedrocamargo.esync.modules.usuario.dto;

import br.com.pedrocamargo.esync.modules.permissao.dto.PermissaoDTO;
import br.com.pedrocamargo.esync.modules.usuario.model.Usuario;

public record UsuarioDTO(Long id, PermissaoDTO permissao, String nome, String usuario, String senha, Boolean is_active) {

    public UsuarioDTO(Usuario usuario){
        this(usuario.getId(), new PermissaoDTO(usuario.getPermissao()), usuario.getNome(), usuario.getUsuario(), usuario.getSenha(), usuario.getIs_active());
    }

}
