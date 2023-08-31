package br.com.pedrocamargo.esync.modules.locacao.dto;

import jakarta.validation.constraints.NotNull;

public record LocacaoDTORequest(
        @NotNull(message = "{notnull}")
        Long idProduto,
        @NotNull(message = "{notnull}")
        Long idLoja
) {}
