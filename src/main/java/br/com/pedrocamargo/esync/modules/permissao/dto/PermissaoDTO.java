package br.com.pedrocamargo.esync.modules.permissao.dto;

import br.com.pedrocamargo.esync.modules.permissao.model.Permissao;

public record PermissaoDTO(Long id, String descricao) {

    public PermissaoDTO(Permissao permissao){
        this(permissao.getId(), permissao.getDescricao());
    }

}
