package br.com.pedrocamargo.esync.modules.usuario.dto;

import br.com.pedrocamargo.esync.modules.permissao.model.Permissao;
import br.com.pedrocamargo.esync.modules.usuario.model.Usuario;

public record UsuarioDTO(Long id, Permissao permissao,String nome,String usuario,String senha,Boolean is_active) {

    public UsuarioDTO(Usuario usuario){
        this(usuario.getId(), usuario.getPermissao(), usuario.getNome(), usuario.getUsuario(), usuario.getSenha(), usuario.getIs_active());
    }

}
