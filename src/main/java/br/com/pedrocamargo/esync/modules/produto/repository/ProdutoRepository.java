package br.com.pedrocamargo.esync.modules.produto.repository;

import br.com.pedrocamargo.esync.modules.produto.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto,Long> {
}
