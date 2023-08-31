package br.com.pedrocamargo.esync.modules.locacao.repository;

import br.com.pedrocamargo.esync.modules.locacao.model.Locacao;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LocacaoRepository extends JpaRepository<Locacao, Long> {
    @Query(value = "SELECT * FROM locacao WHERE id_produto = ?1", nativeQuery = true)
    Locacao getLocacaoByIdProdutoQuery(Long idProduto);
    default Locacao getLocacaoByIdProduto(Long idProduto){
        Locacao locacao = this.getLocacaoByIdProdutoQuery(idProduto);
        if(locacao == null){
            throw  new EntityNotFoundException("Locacao nao encontrada");
        }
        return locacao;
    }


}
