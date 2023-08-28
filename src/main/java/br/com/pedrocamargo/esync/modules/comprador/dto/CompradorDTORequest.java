package br.com.pedrocamargo.esync.modules.comprador.dto;

import br.com.pedrocamargo.esync.modules.comprador.model.Comprador;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public record CompradorDTORequest(
        @NotBlank(message = "{notblank}")
        String nome,
        @NotBlank(message = "{notblank}")
        String sobrenome,
        @NotNull(message = "{notnull}")
        Long cpf,
        @NotNull(message = "{notnull}")
        Long rg,
        @NotNull(message = "{notnull}")
        LocalDate dataNascimento
) {
        public Map<String,Object>toMapAttributes(){
                Map<String,Object> attributesMap = new HashMap<>();

                attributesMap.put("nome",this.nome);
                attributesMap.put("sobrenome",this.sobrenome);
                attributesMap.put("cpf",this.cpf);
                attributesMap.put("rg",this.rg);
                attributesMap.put("dataNascimento",this.dataNascimento);

                return attributesMap;
        }
}