package br.com.pedrocamargo.esync.modules.endereco.dto;

import br.com.pedrocamargo.esync.enums.UFEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.HashMap;
import java.util.Map;

public record EnderecoDTORequest(
        @NotBlank(message = "{notblank}")
        String logradouro,
        @NotBlank(message = "{notblank}")
        String endereco,
        @NotBlank(message = "{notblank}")
        String cidade,
        @NotNull(message = "{notnull")
        UFEnum uf,
        @NotBlank(message = "{notblank}")
        String pais,
        @NotNull(message = "{notnull")
        Long cep) {

        public Map<String,Object> toMapAttributes(){
                Map<String,Object> mapAttributes = new HashMap<>();
                mapAttributes.put("logradouro",this.logradouro);
                mapAttributes.put("endereco",this.endereco);
                mapAttributes.put("cidade",this.cidade);
                mapAttributes.put("uf",this.uf);
                mapAttributes.put("pais",this.pais);
                mapAttributes.put("cep",this.cep);

                return mapAttributes;
        }
}
