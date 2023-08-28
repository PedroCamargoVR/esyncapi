package br.com.pedrocamargo.esync.modules.usuario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.HashMap;
import java.util.Map;

public record UsuarioDTORequest(
        @NotNull(message = "{notnull}")
        Long id_permissao,
        @NotBlank(message = "{notblank}")
        String nome,
        @NotBlank(message = "{notblank}")
        String usuario,
        @NotBlank(message = "{notblank}")
        String senha) {

    public Map<String, Object> mapAttributes(){
        Map<String, Object> mapAttributes = new HashMap<>();
        mapAttributes.put("id_permissao", this.id_permissao);
        mapAttributes.put("nome", this.nome);
        mapAttributes.put("usuario", this.usuario);
        mapAttributes.put("senha", this.senha);

        return mapAttributes;
    }
}
