package br.com.pedrocamargo.esync.modules.fornecedor.dto;

import br.com.pedrocamargo.esync.modules.endereco.model.Endereco;

import java.util.ArrayList;
import java.util.List;

public record FornecedorDTORequest(Endereco endereco, Integer numeroimovel, String nomefantasia, String razaosocial, String cnpj) {
}
