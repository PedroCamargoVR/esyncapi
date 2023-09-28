package br.com.pedrocamargo.esync.modules.loja.service;

import br.com.pedrocamargo.esync.modules.endereco.model.Endereco;
import br.com.pedrocamargo.esync.modules.endereco.repository.EnderecoRepository;
import br.com.pedrocamargo.esync.modules.loja.dto.LojaDTO;
import br.com.pedrocamargo.esync.modules.loja.dto.LojaDTORequest;
import br.com.pedrocamargo.esync.modules.loja.model.Loja;
import br.com.pedrocamargo.esync.modules.loja.repository.LojaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LojaService {
    @Autowired
    private LojaRepository repository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    public Page<LojaDTO> getLojas(Pageable pageable){
        Page<LojaDTO> page = repository.findAll(pageable).map(LojaDTO::new);
        return page;
    }

    public Loja getLoja(Long idLoja){
        Loja loja = repository.getReferenceById(idLoja);
        return loja;
    }

    public Loja adicionarLoja(LojaDTORequest lojaRequest){
        Endereco endereco = enderecoRepository.getReferenceById(lojaRequest.id_endereco());

        Loja lojaInserida = repository.save(new Loja(endereco,lojaRequest));

        return lojaInserida;
    }

    public Loja atualizarLoja(Long idLoja, LojaDTORequest lojaRequest){
        Loja loja = repository.getReferenceById(idLoja);
        if(loja.getEndereco().getId() != lojaRequest.id_endereco()){
            Endereco endereco = enderecoRepository.getReferenceById(lojaRequest.id_endereco());
            loja.update(endereco, lojaRequest);
        }
        loja.update(lojaRequest);
        return loja;
    }

    public void deletarLoja(Long idLoja){
        Loja loja = repository.getReferenceById(idLoja);
        loja.delete();
    }


}
