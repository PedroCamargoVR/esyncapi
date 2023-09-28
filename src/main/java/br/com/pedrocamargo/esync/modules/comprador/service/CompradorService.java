package br.com.pedrocamargo.esync.modules.comprador.service;

import br.com.pedrocamargo.esync.modules.comprador.dto.CompradorDTO;
import br.com.pedrocamargo.esync.modules.comprador.dto.CompradorDTORequest;
import br.com.pedrocamargo.esync.modules.comprador.model.Comprador;
import br.com.pedrocamargo.esync.modules.comprador.repository.CompradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CompradorService {

    @Autowired
    CompradorRepository repository;

    public Page<CompradorDTO> getCompradores(Pageable pageable){
        Page<CompradorDTO> page = repository.findAll(pageable).map(CompradorDTO::new);
        return page;
    }

    public Comprador getComprador(Long idComprador){
        Comprador comprador = repository.getReferenceById(idComprador);
        return comprador;
    }

    public Comprador salvarComprador(CompradorDTORequest compradorDTORequest){
        Comprador comprador = repository.save(new Comprador(compradorDTORequest));
        return comprador;
    }

    public Comprador atualizarComprador(Long idComprador, CompradorDTORequest compradorDTORequest){
        Comprador compradorBanco = repository.getReferenceById(idComprador);
        compradorBanco.update(compradorDTORequest);
        return compradorBanco;
    }

    public void deletarComprador(Long idComprador){
        Comprador comprador = repository.getReferenceById(idComprador);
        comprador.delete();
    }

}
