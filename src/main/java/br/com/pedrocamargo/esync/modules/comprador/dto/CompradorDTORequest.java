package br.com.pedrocamargo.esync.modules.comprador.dto;

import br.com.pedrocamargo.esync.modules.comprador.model.Comprador;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public record CompradorDTORequest(
        @NotNull
        @NotBlank
        String nome,
        @NotNull
        @NotBlank
        String sobrenome,
        @NotNull
        Long cpf,
        @NotNull
        Long rg,
        @NotNull
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