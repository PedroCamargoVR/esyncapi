package br.com.pedrocamargo.esync.modules.endereco.repository;

import br.com.pedrocamargo.esync.modules.endereco.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
