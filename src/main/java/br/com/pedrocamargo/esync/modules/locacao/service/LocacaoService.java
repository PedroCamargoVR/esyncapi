package br.com.pedrocamargo.esync.modules.locacao.service;

import br.com.pedrocamargo.esync.modules.locacao.dto.LocacaoDTO;
import br.com.pedrocamargo.esync.modules.locacao.dto.LocacaoDTORequest;
import br.com.pedrocamargo.esync.modules.locacao.dto.LocacaoDTOUpdate;
import br.com.pedrocamargo.esync.modules.locacao.model.Locacao;
import br.com.pedrocamargo.esync.modules.locacao.repository.LocacaoRepository;
import br.com.pedrocamargo.esync.modules.loja.model.Loja;
import br.com.pedrocamargo.esync.modules.loja.repository.LojaRepository;
import br.com.pedrocamargo.esync.modules.produto.model.Produto;
import br.com.pedrocamargo.esync.modules.produto.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LocacaoService {

    @Autowired
    private LocacaoRepository repository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private LojaRepository lojaRepository;

    public Page<LocacaoDTO> getLocacoes(Pageable pageable){
        Page<LocacaoDTO> page = repository.findAll(pageable).map(LocacaoDTO::new);
        return page;
    }

    public LocacaoDTO getLocacao(Long idProduto){
        LocacaoDTO locacaoDTO = new LocacaoDTO(repository.getLocacaoByIdProduto(idProduto));
        return  locacaoDTO;
    }

    public Locacao adicionarLocacao(LocacaoDTORequest locacaoRequest){
        Produto produto = produtoRepository.getReferenceById(locacaoRequest.idProduto());
        Loja loja = lojaRepository.getReferenceById(locacaoRequest.idLoja());
        Locacao locacao = repository.save(new Locacao(null,produto,loja));
        return locacao;
    }

    public Locacao atualizarLocacao(Long idProduto, LocacaoDTOUpdate locacaoDTORequest){
        Loja loja = lojaRepository.getReferenceById(locacaoDTORequest.idLoja());
        Locacao locacao = repository.getLocacaoByIdProduto(idProduto);
        locacao.update(loja);
        return locacao;
    }
}
