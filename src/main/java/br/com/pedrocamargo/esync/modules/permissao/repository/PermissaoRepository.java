package br.com.pedrocamargo.esync.modules.permissao.repository;

import br.com.pedrocamargo.esync.modules.permissao.model.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissaoRepository extends JpaRepository<Permissao,Long> {
}
