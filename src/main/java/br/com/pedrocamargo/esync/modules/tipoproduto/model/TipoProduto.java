package br.com.pedrocamargo.esync.modules.tipoproduto.model;

import br.com.pedrocamargo.esync.modules.tipoproduto.dto.TipoProdutoDTORequest;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table(name = "tipoproduto")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class TipoProduto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;

    public TipoProduto(String descricao){
        this.descricao = descricao;
    }

    public void update(TipoProdutoDTORequest tipoProdutoRequest) {
        this.descricao = tipoProdutoRequest.descricao();
    }
}
