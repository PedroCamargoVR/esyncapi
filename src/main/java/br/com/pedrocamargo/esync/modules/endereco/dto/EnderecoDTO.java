package br.com.pedrocamargo.esync.modules.endereco.dto;

import br.com.pedrocamargo.esync.enums.UFEnum;
import br.com.pedrocamargo.esync.modules.endereco.model.Endereco;

public record EnderecoDTO(Long id, String logradouro, String endereco, String cidade, UFEnum uf, String pais, Long cep) {

    public EnderecoDTO(Endereco endereco){
        this(endereco.getId(), endereco.getLogradouro(), endereco.getEndereco(), endereco.getCidade(), UFEnum.getEnum(endereco.getUf()), endereco.getPais(), endereco.getCep());
    }

}