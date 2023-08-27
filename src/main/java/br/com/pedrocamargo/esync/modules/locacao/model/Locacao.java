package br.com.pedrocamargo.esync.modules.locacao.model;

import jakarta.persistence.*;

@Entity
@Table(name = "locacao")
public class Locacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
}
