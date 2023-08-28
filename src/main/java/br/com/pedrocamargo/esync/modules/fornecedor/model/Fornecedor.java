package br.com.pedrocamargo.esync.modules.fornecedor.model;

import br.com.pedrocamargo.esync.modules.fornecedor.dto.FornecedorDTORequest;
import br.com.pedrocamargo.esync.modules.endereco.model.Endereco;
import br.com.pedrocamargo.esync.modules.produto.model.Produto;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Map;

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
    @OneToMany
    @JoinColumn(name = "id_fornecedor")
    private List<Produto> produtos;

    public Fornecedor(Endereco endereco,FornecedorDTORequest fornecedorRequest){
        this.endereco = endereco;
        this.numeroimovel = fornecedorRequest.numeroimovel();
        this.nomefantasia = fornecedorRequest.nomefantasia();
        this.razaosocial = fornecedorRequest.razaosocial();
        this.cnpj = fornecedorRequest.cnpj();
    }

    public void update(Endereco endereco, FornecedorDTORequest fornecedorRequest) {
        this.endereco = endereco;
        Map<String,Object> mapAttributes = fornecedorRequest.mapAttributes();
        for(String keyAttribute : mapAttributes.keySet()){
            if(mapAttributes.get(keyAttribute) != null){
                this.setAttributeByNameString(keyAttribute,mapAttributes.get(keyAttribute));
            }
        }
    }

    private void setAttributeByNameString(String keyAttribute, Object o) {
        switch (keyAttribute){
            case "numeroimovel":
                this.numeroimovel = (Integer) o;
                break;
            case "nomefantasia":
                this.nomefantasia = (String) o;
                break;
            case "razaosocial":
                this.razaosocial = (String) o;
                break;
            case "cnpj":
                this.cnpj = (String) o;
                break;
        }
    }

    public void delete(){
        this.is_active = false;
    }
}
