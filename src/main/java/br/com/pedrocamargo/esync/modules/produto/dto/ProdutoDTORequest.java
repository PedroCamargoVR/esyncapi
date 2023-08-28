package br.com.pedrocamargo.esync.modules.produto.dto;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public record ProdutoDTORequest(Long id_fornecedor, Long id_comprador, Long id_tipoproduto, String descricao, Double preco, LocalDate datacompra) {

    public Map<String,Object> mapAttributes(){
        Map<String,Object> mapAttributes = new HashMap<>();
        mapAttributes.put("descricao",this.descricao);
        mapAttributes.put("preco",this.preco);
        mapAttributes.put("datacompra",this.datacompra);
        return mapAttributes;
    }

}
