package br.com.pedrocamargo.esync.modules.locacao.dto;

import jakarta.validation.constraints.NotNull;

public record LocacaoDTOUpdate(@NotNull(message = "{notnull}") Long idLoja) {
}
