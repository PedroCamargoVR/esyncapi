package br.com.pedrocamargo.esync.modules.autenticacao.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AutenticacaoDTO(
        @NotNull(message = "Usuario nao pode ser nulo")
        @NotBlank(message = "Usuario nao pode estar em branco")
        String usuario,
        @NotNull(message = "Senha nao pode ser nula")
        @NotBlank(message = "Senha nao pode estar em branco")
        String senha
) {}
