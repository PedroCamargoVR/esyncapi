package br.com.pedrocamargo.esync.modules.endereco.model;

import br.com.pedrocamargo.esync.modules.endereco.dto.EnderecoDTORequest;
import br.com.pedrocamargo.esync.modules.fornecedor.model.Fornecedor;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Map;

@Entity
@Table(name = "endereco")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String logradouro;
    private String endereco;
    private String cidade;
    private String uf;
    private String pais;
    Long cep;
    @OneToMany
    @JoinColumn(name = "id_endereco")
    private List<Fornecedor> fornecedores;

    public Endereco(EnderecoDTORequest enderecoDTORequest){
        this.logradouro = enderecoDTORequest.logradouro();
        this.endereco = enderecoDTORequest.endereco();
        this.cidade = enderecoDTORequest.cidade();
        this.uf = enderecoDTORequest.uf().getDescription();
        this.pais = enderecoDTORequest.pais();
        this.cep = enderecoDTORequest.cep();
    }

    public void update(EnderecoDTORequest enderecoRequest) {
        Map<String,Object> mapAttributes = enderecoRequest.toMapAttributes();
        for(String key : mapAttributes.keySet()){
            if(mapAttributes.get(key) != null){
                this.setAttributeByNameString(key,mapAttributes.get(key));
            }
        }
    }

    private void setAttributeByNameString(String key, Object o) {
        switch (key){
            case "logradouro":
                this.logradouro = (String) o;
                break;
            case "endereco":
                this.endereco = (String) o;
                break;
            case "cidade":
                this.cidade = (String) o;
                break;
            case "uf":
                this.uf = (String) o;
                break;
            case "pais":
                this.pais = (String) o;
                break;
            case "cep":
                this.cep = (Long) o;
                break;
        }
    }
}
