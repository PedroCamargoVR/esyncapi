package br.com.pedrocamargo.esync.modules.loja.model;

import br.com.pedrocamargo.esync.modules.endereco.model.Endereco;
import br.com.pedrocamargo.esync.modules.locacao.model.Locacao;
import br.com.pedrocamargo.esync.modules.loja.dto.LojaDTORequest;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "loja")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Loja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_endereco")
    private Endereco endereco;
    private Integer numeroimovel;
    private String nomefantasia;
    private String razaosocial;
    private String cnpj;
    private Boolean is_active = true;

    @OneToMany
    @JoinColumn(name = "id_produto")
    List<Locacao> locacoes;

    public Loja(Endereco endereco, LojaDTORequest lojaRequest){
        this.endereco = endereco;
        this.numeroimovel = lojaRequest.numeroimovel();
        this.nomefantasia = lojaRequest.nomefantasia();
        this.razaosocial = lojaRequest.razaosocial();
        this.cnpj = lojaRequest.cnpj();
    }

    public void update(LojaDTORequest lojaRequest) {
        if(lojaRequest.numeroimovel() != null){
            this.numeroimovel = lojaRequest.numeroimovel();
        }
        if(lojaRequest.nomefantasia() != null){
            this.nomefantasia = lojaRequest.nomefantasia();
        }
        if(lojaRequest.razaosocial() != null){
            this.razaosocial = lojaRequest.razaosocial();
        }
        if(lojaRequest.cnpj() != null){
            this.cnpj = lojaRequest.cnpj();
        }
    }

    public void update(Endereco endereco, LojaDTORequest lojaRequest) {
        this.endereco = endereco;
        if(lojaRequest.numeroimovel() != null){
            this.numeroimovel = lojaRequest.numeroimovel();
        }
        if(lojaRequest.nomefantasia() != null){
            this.nomefantasia = lojaRequest.nomefantasia();
        }
        if(lojaRequest.razaosocial() != null){
            this.razaosocial = lojaRequest.razaosocial();
        }
        if(lojaRequest.cnpj() != null){
            this.cnpj = lojaRequest.cnpj();
        }
    }

    public void delete(){
        this.is_active = false;
    }
}
