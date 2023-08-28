package br.com.pedrocamargo.esync.modules.produto.dto;

import br.com.pedrocamargo.esync.modules.comprador.model.Comprador;
import br.com.pedrocamargo.esync.modules.fornecedor.model.Fornecedor;
import br.com.pedrocamargo.esync.modules.produto.model.Produto;
import br.com.pedrocamargo.esync.modules.tipoproduto.model.TipoProduto;

import java.time.LocalDate;

public record ProdutoDTO(Long id, Fornecedor fornecedor, Comprador comprador, TipoProduto tipoProduto, String descricao, Double preco, LocalDate datacompra, Boolean is_active) {

    public ProdutoDTO(Produto produto){
        this(produto.getId(), produto.getFornecedor(), produto.getComprador(), produto.getTipoProduto(), produto.getDescricao(), produto.getPreco(), produto.getDatacompra(),produto.getIs_active());
    }

}
