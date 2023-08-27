package br.com.pedrocamargo.esync.modules.loja.repository;

import br.com.pedrocamargo.esync.modules.loja.model.Loja;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LojaRepository extends JpaRepository<Loja, Long> {
}
