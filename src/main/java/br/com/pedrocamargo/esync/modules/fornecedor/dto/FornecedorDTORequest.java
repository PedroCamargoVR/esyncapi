package br.com.pedrocamargo.esync.modules.fornecedor.dto;

import br.com.pedrocamargo.esync.modules.endereco.model.Endereco;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record FornecedorDTORequest(
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

    public Map<String,Object> mapAttributes(){
        Map<String, Object> mapAttribute = new HashMap<>();
        mapAttribute.put("numeroimovel",numeroimovel);
        mapAttribute.put("nomefantasia",nomefantasia);
        mapAttribute.put("razaosocial",razaosocial);
        mapAttribute.put("cnpj",cnpj);

        return mapAttribute;
    }

}
