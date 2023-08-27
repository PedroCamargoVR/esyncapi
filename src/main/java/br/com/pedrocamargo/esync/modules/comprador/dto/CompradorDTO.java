package br.com.pedrocamargo.esync.modules.comprador.dto;

import br.com.pedrocamargo.esync.modules.comprador.model.Comprador;

import java.time.LocalDate;

public record CompradorDTO(Long id, String nome, String sobrenome, Long cpf, Long rg, LocalDate dataNascimento, Boolean isActive) {

    public CompradorDTO(Comprador comprador){
        this(comprador.getId(), comprador.getNome(), comprador.getSobrenome(), comprador.getCpf(), comprador.getRg(), comprador.getDatanascimento(), comprador.getIs_active());
    }
}