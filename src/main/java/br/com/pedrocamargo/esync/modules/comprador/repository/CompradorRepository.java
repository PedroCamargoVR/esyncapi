package br.com.pedrocamargo.esync.modules.comprador.repository;

import br.com.pedrocamargo.esync.modules.comprador.model.Comprador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompradorRepository extends JpaRepository<Comprador,Long> {
}
