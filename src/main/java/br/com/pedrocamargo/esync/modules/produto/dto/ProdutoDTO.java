package br.com.pedrocamargo.esync.modules.produto.dto;

import br.com.pedrocamargo.esync.modules.comprador.dto.CompradorDTO;
import br.com.pedrocamargo.esync.modules.fornecedor.dto.FornecedorDTO;
import br.com.pedrocamargo.esync.modules.produto.model.Produto;
import br.com.pedrocamargo.esync.modules.tipoproduto.dto.TipoProdutoDTO;

import java.time.LocalDate;

public record ProdutoDTO(Long id, FornecedorDTO fornecedor, CompradorDTO comprador, TipoProdutoDTO tipoproduto, String descricao, Double preco, LocalDate datacompra, Boolean is_active) {

    public ProdutoDTO(Produto produto){
        this(produto.getId(), new FornecedorDTO(produto.getFornecedor()), new CompradorDTO(produto.getComprador()), new TipoProdutoDTO(produto.getTipoProduto()), produto.getDescricao(), produto.getPreco(), produto.getDatacompra(),produto.getIs_active());
    }

}
