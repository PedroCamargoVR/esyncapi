package br.com.pedrocamargo.esync.modules.tipoproduto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TipoProdutoDTORequest(
        @NotBlank(message = "{notblank}")
        String descricao) {
}
