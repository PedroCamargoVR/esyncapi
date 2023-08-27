package br.com.pedrocamargo.esync.modules.tipoproduto.repository;

import br.com.pedrocamargo.esync.modules.tipoproduto.model.TipoProduto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoProdutoRepository extends JpaRepository<TipoProduto,Long> {
}
