package br.com.pedrocamargo.esync.modules.produto.service;

import br.com.pedrocamargo.esync.modules.comprador.model.Comprador;
import br.com.pedrocamargo.esync.modules.comprador.repository.CompradorRepository;
import br.com.pedrocamargo.esync.modules.fornecedor.model.Fornecedor;
import br.com.pedrocamargo.esync.modules.fornecedor.repository.FornecedorRepository;
import br.com.pedrocamargo.esync.modules.produto.dto.ProdutoDTO;
import br.com.pedrocamargo.esync.modules.produto.dto.ProdutoDTORequest;
import br.com.pedrocamargo.esync.modules.produto.model.Produto;
import br.com.pedrocamargo.esync.modules.produto.repository.ProdutoRepository;
import br.com.pedrocamargo.esync.modules.tipoproduto.model.TipoProduto;
import br.com.pedrocamargo.esync.modules.tipoproduto.repository.TipoProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private CompradorRepository compradorRepository;

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Autowired
    private TipoProdutoRepository tipoProdutoRepository;

    public Page<ProdutoDTO> getProdutos(Pageable pageable){
        Page<ProdutoDTO> page = repository.findAll(pageable).map(ProdutoDTO::new);
        return page;
    }

    public Produto getProduto(Long idProduto){
        Produto produto = repository.getReferenceById(idProduto);
        return produto;
    }

    public Produto salvarProduto(ProdutoDTORequest produtoRequest){
        Fornecedor fornecedor = fornecedorRepository.getReferenceById(produtoRequest.id_fornecedor());
        Comprador comprador = compradorRepository.getReferenceById(produtoRequest.id_comprador());
        TipoProduto tipoProduto = tipoProdutoRepository.getReferenceById(produtoRequest.id_tipoproduto());
        Produto produto = repository.save(new Produto(fornecedor,comprador,tipoProduto,produtoRequest));
        return produto;
    }

    public Produto atualizarProduto(Long idProduto,ProdutoDTORequest produtoDTORequest){
        Produto produto = repository.getReferenceById(idProduto);
        Fornecedor fornecedor = produto.getFornecedor();
        Comprador comprador = produto.getComprador();
        TipoProduto tipoProduto = produto.getTipoProduto();

        if(fornecedor.getId() != produtoDTORequest.id_fornecedor()){
            fornecedor = fornecedorRepository.getReferenceById(produtoDTORequest.id_fornecedor());
        }
        if(comprador.getId() != produtoDTORequest.id_comprador()){
            comprador = compradorRepository.getReferenceById(produtoDTORequest.id_comprador());
        }
        if(tipoProduto.getId() != produtoDTORequest.id_tipoproduto()){
            tipoProduto = tipoProdutoRepository.getReferenceById(produtoDTORequest.id_tipoproduto());
        }
        produto.update(fornecedor,comprador,tipoProduto,produtoDTORequest);
        return produto;
    }

    public void deletarProduto(Long idProduto){
        Produto produto = repository.getReferenceById(idProduto);
        produto.delete();
    }

}
