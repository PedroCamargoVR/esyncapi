package br.com.pedrocamargo.esync.modules.permissao.dto;

import br.com.pedrocamargo.esync.modules.permissao.model.Permissao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PermissaoDTORequest(
        @NotBlank(message = "{notblank}")
        String descricao) {}
