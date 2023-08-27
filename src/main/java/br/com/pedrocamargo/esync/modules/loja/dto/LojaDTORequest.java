package br.com.pedrocamargo.esync.modules.loja.dto;

import br.com.pedrocamargo.esync.modules.endereco.model.Endereco;
import br.com.pedrocamargo.esync.modules.loja.model.Loja;

public record LojaDTORequest(Long id_endereco, Integer numeroimovel, String nomefantasia, String razaosocial, String cnpj) {

    public LojaDTORequest(Loja loja){
        this(loja.getEndereco().getId(),loja.getNumeroimovel(),loja.getNomefantasia(),loja.getRazaosocial(),loja.getCnpj());
    }
}
