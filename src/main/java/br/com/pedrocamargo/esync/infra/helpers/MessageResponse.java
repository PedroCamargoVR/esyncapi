package br.com.pedrocamargo.esync.infra.helpers;

import io.swagger.v3.oas.models.media.Content;

public record MessageResponse(Integer httpStatusCode, String message) {
}
