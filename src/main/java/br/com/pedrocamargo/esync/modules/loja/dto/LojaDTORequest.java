package br.com.pedrocamargo.esync.modules.loja.dto;

import br.com.pedrocamargo.esync.modules.endereco.model.Endereco;
import br.com.pedrocamargo.esync.modules.loja.model.Loja;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LojaDTORequest(
        @NotNull(message = "{notnull}")
        Long id_endereco,
        @NotNull(message = "{notnull}")
        Integer numeroimovel,
        @NotBlank(message = "{notblank}")
        String nomefantasia,
        @NotBlank(message = "{notblank}")
        String razaosocial,
        @NotBlank(message = "{notblank}")
        String cnpj) {

    public LojaDTORequest(Loja loja){
        this(loja.getEndereco().getId(),loja.getNumeroimovel(),loja.getNomefantasia(),loja.getRazaosocial(),loja.getCnpj());
    }
}
