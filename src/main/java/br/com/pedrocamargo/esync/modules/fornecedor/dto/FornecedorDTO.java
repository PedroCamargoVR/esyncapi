package br.com.pedrocamargo.esync.modules.fornecedor.dto;

import br.com.pedrocamargo.esync.modules.endereco.dto.EnderecoDTO;
import br.com.pedrocamargo.esync.modules.endereco.model.Endereco;
import br.com.pedrocamargo.esync.modules.fornecedor.model.Fornecedor;

public record FornecedorDTO(Long id, EnderecoDTO endereco, Integer numeroimovel, String nomefantasia, String razaosocial, String cnpj) {

    public FornecedorDTO(Fornecedor fornecedor){
        this(fornecedor.getId(), new EnderecoDTO(fornecedor.getEndereco()), fornecedor.getNumeroimovel(), fornecedor.getNomefantasia(), fornecedor.getRazaosocial(), fornecedor.getCnpj());
    }

}
