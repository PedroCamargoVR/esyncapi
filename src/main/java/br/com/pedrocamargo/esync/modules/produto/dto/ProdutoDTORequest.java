package br.com.pedrocamargo.esync.modules.produto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public record ProdutoDTORequest(
        @NotNull(message = "{notnull}")
        Long id_fornecedor,
        @NotNull(message = "{notnull}")
        Long id_comprador,
        @NotNull(message = "{notnull}")
        Long id_tipoproduto,
        @NotBlank(message = "{notblank}")
        String descricao,
        @NotNull(message = "{notnull}")
        Double preco,
        @NotNull(message = "{notnull}")
        LocalDate datacompra) {

    public Map<String,Object> mapAttributes(){
        Map<String,Object> mapAttributes = new HashMap<>();
        mapAttributes.put("descricao",this.descricao);
        mapAttributes.put("preco",this.preco);
        mapAttributes.put("datacompra",this.datacompra);
        return mapAttributes;
    }

}
