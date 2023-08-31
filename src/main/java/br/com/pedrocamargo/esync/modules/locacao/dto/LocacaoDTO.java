package br.com.pedrocamargo.esync.modules.locacao.dto;

import br.com.pedrocamargo.esync.modules.locacao.model.Locacao;
import br.com.pedrocamargo.esync.modules.loja.dto.LojaDTO;
import br.com.pedrocamargo.esync.modules.produto.dto.ProdutoDTO;

public record LocacaoDTO(Long idLocacao, ProdutoDTO produto, LojaDTO loja) {

    public LocacaoDTO(Locacao locacao){
        this(locacao.getId(),new ProdutoDTO(locacao.getProduto()),new LojaDTO(locacao.getLoja()));
    }
}
