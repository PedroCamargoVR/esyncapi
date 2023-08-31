package br.com.pedrocamargo.esync.modules.locacao.model;

import br.com.pedrocamargo.esync.modules.locacao.dto.LocacaoDTORequest;
import br.com.pedrocamargo.esync.modules.loja.model.Loja;
import br.com.pedrocamargo.esync.modules.produto.model.Produto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "locacao")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Locacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @OneToOne
    @JoinColumn(name = "id_produto")
    private Produto produto;
    @ManyToOne
    @JoinColumn(name = "id_loja")
    private Loja loja;


    public void update(Loja loja) {
        this.loja = loja;
    }
}
