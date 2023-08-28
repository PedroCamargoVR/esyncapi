package br.com.pedrocamargo.esync.modules.tipoproduto.model;

import br.com.pedrocamargo.esync.modules.produto.model.Produto;
import br.com.pedrocamargo.esync.modules.tipoproduto.dto.TipoProdutoDTORequest;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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
    @OneToMany
    @JoinColumn(name = "id_tipoproduto")
    private List<Produto> produtos;

    public TipoProduto(String descricao){
        this.descricao = descricao;
    }

    public void update(TipoProdutoDTORequest tipoProdutoRequest) {
        this.descricao = tipoProdutoRequest.descricao();
    }
}
