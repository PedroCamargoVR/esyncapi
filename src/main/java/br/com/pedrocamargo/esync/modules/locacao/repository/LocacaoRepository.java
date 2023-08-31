package br.com.pedrocamargo.esync.modules.locacao.repository;

import br.com.pedrocamargo.esync.modules.locacao.model.Locacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocacaoRepository extends JpaRepository<Locacao, Long> {
    Locacao getReferenceByIdProduto(Long idProduto);
}
