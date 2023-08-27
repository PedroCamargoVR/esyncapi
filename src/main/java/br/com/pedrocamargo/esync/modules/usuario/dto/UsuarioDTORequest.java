package br.com.pedrocamargo.esync.modules.usuario.dto;

import java.util.HashMap;
import java.util.Map;

public record UsuarioDTORequest(Long id_permissao, String nome, String usuario, String senha) {

    public Map<String, Object> mapAttributes(){
        Map<String, Object> mapAttributes = new HashMap<>();
        mapAttributes.put("id_permissao", this.id_permissao);
        mapAttributes.put("nome", this.nome);
        mapAttributes.put("usuario", this.usuario);
        mapAttributes.put("senha", this.senha);

        return mapAttributes;
    }
}
