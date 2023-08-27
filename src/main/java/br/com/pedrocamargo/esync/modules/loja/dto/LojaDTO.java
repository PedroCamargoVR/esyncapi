package br.com.pedrocamargo.esync.modules.loja.dto;

import br.com.pedrocamargo.esync.modules.endereco.model.Endereco;
import br.com.pedrocamargo.esync.modules.loja.model.Loja;

public record LojaDTO(Long id, Endereco endereco, Integer numeroimovel, String nomefantasia, String razaosocial, String cnpj) {

    public LojaDTO(Loja loja){
        this(loja.getId(),loja.getEndereco(),loja.getNumeroimovel(),loja.getNomefantasia(),loja.getRazaosocial(),loja.getCnpj());
    }
}
