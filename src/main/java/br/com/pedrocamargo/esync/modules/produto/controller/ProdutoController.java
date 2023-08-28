package br.com.pedrocamargo.esync.modules.produto.controller;

import br.com.pedrocamargo.esync.helpers.MessageResponse;
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
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("produto")
public class ProdutoController {

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private CompradorRepository compradorRepository;

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Autowired
    private TipoProdutoRepository tipoProdutoRepository;

    @GetMapping
    public ResponseEntity<Page<ProdutoDTO>> getAllProdutos(@PageableDefault(sort = "id", size = 20) Pageable pageable){
        Page<ProdutoDTO> page = repository.findAll(pageable).map(ProdutoDTO::new);

        return ResponseEntity.ok(page);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProdutoDTO> getProdutoById(@PathVariable("id") Long idProduto){
        Produto produto = repository.getReferenceById(idProduto);

        return ResponseEntity.ok(new ProdutoDTO(produto));
    }

    @PostMapping
    @Transactional
    public ResponseEntity addProduto(@RequestBody ProdutoDTORequest produtoRequest, UriComponentsBuilder uriBUilder){
        Fornecedor fornecedor = fornecedorRepository.getReferenceById(produtoRequest.id_fornecedor());
        Comprador comprador = compradorRepository.getReferenceById(produtoRequest.id_comprador());
        TipoProduto tipoProduto = tipoProdutoRepository.getReferenceById(produtoRequest.id_tipoproduto());
        Produto produto = repository.save(new Produto(fornecedor,comprador,tipoProduto,produtoRequest));

        URI uri = uriBUilder.path("produto/{id}").buildAndExpand(produto.getId()).toUri();

        return ResponseEntity.created(uri).body(new ProdutoDTO(produto));
    }

    @PutMapping("{id}")
    @Transactional
    public ResponseEntity updateProduto(@PathVariable("id") Long idProduto, @RequestBody ProdutoDTORequest produtoDTORequest){
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

        return ResponseEntity.ok(new ProdutoDTO(produto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteProduto(@PathVariable("id") Long idProduto){
        Produto produto = repository.getReferenceById(idProduto);
        produto.delete();

        return ResponseEntity.ok(new MessageResponse(HttpStatus.OK.value(),"O produto com ID " + idProduto + " foi excluido."));
    }
}
