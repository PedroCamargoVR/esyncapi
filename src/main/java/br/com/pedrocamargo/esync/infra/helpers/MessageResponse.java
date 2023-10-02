package br.com.pedrocamargo.esync.infra.helpers;

public record MessageResponse(Integer httpStatusCode, String message) {
}
