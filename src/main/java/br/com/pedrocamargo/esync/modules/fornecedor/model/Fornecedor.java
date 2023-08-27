package br.com.pedrocamargo.esync.modules.fornecedor.model;

import br.com.pedrocamargo.esync.modules.fornecedor.dto.FornecedorDTORequest;
import br.com.pedrocamargo.esync.modules.endereco.model.Endereco;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "fornecedor")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Fornecedor {
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

    public Fornecedor(FornecedorDTORequest fornecedorRequest){
        this.endereco = fornecedorRequest.endereco();
        this.numeroimovel = fornecedorRequest.numeroimovel();
        this.nomefantasia = fornecedorRequest.nomefantasia();
        this.razaosocial = fornecedorRequest.razaosocial();
        this.cnpj = fornecedorRequest.cnpj();
    }

    public void update(FornecedorDTORequest fornecedorRequest) {
        if(fornecedorRequest.endereco() != null){
            this.endereco = fornecedorRequest.endereco();
        }
        if(fornecedorRequest.numeroimovel() != null){
            this.numeroimovel = fornecedorRequest.numeroimovel();
        }
        if(fornecedorRequest.nomefantasia() != null){
            this.nomefantasia = fornecedorRequest.nomefantasia();
        }
        if(fornecedorRequest.razaosocial() != null){
            this.razaosocial = fornecedorRequest.razaosocial();
        }
        if(fornecedorRequest.cnpj() != null){
            this.cnpj = fornecedorRequest.cnpj();
        }
    }

    public void delete(){
        this.is_active = false;
    }
}
