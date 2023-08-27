package br.com.pedrocamargo.esync.modules.endereco.dto;

import br.com.pedrocamargo.esync.enums.UFEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.HashMap;
import java.util.Map;

public record EnderecoDTORequest(
        @NotBlank
        String logradouro,
        @NotBlank
        String endereco,
        @NotBlank
        String cidade,
        @NotBlank
        UFEnum uf,
        @NotBlank
        String pais,
        @NotBlank
        @NotNull
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
