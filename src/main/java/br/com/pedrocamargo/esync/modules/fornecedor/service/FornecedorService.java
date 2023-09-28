package br.com.pedrocamargo.esync.modules.fornecedor.service;

import br.com.pedrocamargo.esync.modules.endereco.model.Endereco;
import br.com.pedrocamargo.esync.modules.endereco.repository.EnderecoRepository;
import br.com.pedrocamargo.esync.modules.fornecedor.dto.FornecedorDTO;
import br.com.pedrocamargo.esync.modules.fornecedor.dto.FornecedorDTORequest;
import br.com.pedrocamargo.esync.modules.fornecedor.model.Fornecedor;
import br.com.pedrocamargo.esync.modules.fornecedor.repository.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FornecedorService {

    @Autowired
    FornecedorRepository repository;

    @Autowired
    EnderecoRepository enderecoRepository;

    public Page<FornecedorDTO> getFornecedores(Pageable pageable){
        Page<FornecedorDTO> page = repository.findAll(pageable).map(FornecedorDTO::new);
        return page;
    }

    public Fornecedor getFornecedor(Long idFornecedor){
        return repository.getReferenceById(idFornecedor);
    }

    public Fornecedor adicionarFornecedor(FornecedorDTORequest fornecedorRequest){
        Endereco endereco = enderecoRepository.getReferenceById(fornecedorRequest.id_endereco());
        Fornecedor fornecedor = repository.save(new Fornecedor(endereco,fornecedorRequest));
        return fornecedor;
    }

    public Fornecedor atualizarFornecedor(Long idFornecedor, FornecedorDTORequest fornecedorRequest){
        Fornecedor fornecedor = repository.getReferenceById(idFornecedor);
        Endereco endereco = fornecedor.getEndereco();
        if(fornecedor.getEndereco().getId() != fornecedorRequest.id_endereco()){
            endereco = enderecoRepository.getReferenceById(fornecedorRequest.id_endereco());
        }
        fornecedor.update(endereco,fornecedorRequest);

        return fornecedor;
    }

    public void deletarFornecedor(Long idFornecedor){
        Fornecedor fornecedor = repository.getReferenceById(idFornecedor);
        fornecedor.delete();
    }

}
