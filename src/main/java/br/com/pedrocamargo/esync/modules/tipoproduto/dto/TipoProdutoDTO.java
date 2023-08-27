package br.com.pedrocamargo.esync.modules.tipoproduto.dto;

import br.com.pedrocamargo.esync.modules.tipoproduto.model.TipoProduto;

public record TipoProdutoDTO(Long id, String descricao) {

    public TipoProdutoDTO(TipoProduto tipoProduto){
        this(tipoProduto.getId(), tipoProduto.getDescricao());
    }

}
