package br.com.pedrocamargo.esync.modules.comprador.model;

import br.com.pedrocamargo.esync.modules.comprador.dto.CompradorDTORequest;
import br.com.pedrocamargo.esync.modules.produto.model.Produto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Entity(name = "Comprador")
@Table(name = "comprador")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comprador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String sobrenome;
    private Long cpf;
    private Long rg;
    private LocalDate datanascimento;
    private Boolean is_active = true;
    @OneToMany
    @JoinColumn(name = "id_comprador")
    private List<Produto> produtos;

    public Comprador(CompradorDTORequest comprador) {
        this.nome = comprador.nome();
        this.sobrenome = comprador.sobrenome();
        this.cpf = comprador.cpf();
        this.rg = comprador.rg();
        this.datanascimento = comprador.dataNascimento();
    }

    public void update(CompradorDTORequest comprador) {
        Map<String,Object> attributesMap = comprador.toMapAttributes();
        for(String key : attributesMap.keySet()){
            if(attributesMap.get(key) != null){
                this.setAttributeByNameString(key,attributesMap.get(key));
            }
        }
    }

    private void setAttributeByNameString(String key, Object o) {
        switch (key){
            case "nome":
                this.nome = (String) o;
                break;
            case "sobrenome":
                this.sobrenome = (String) o;
                break;
            case "cpf":
                this.cpf = (Long) o;
                break;
            case "rg":
                this.rg = (Long) o;
                break;
            case "datanascimento":
                this.datanascimento = (LocalDate) o;
                break;
        }
    }

    public void delete(){
        this.is_active = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Comprador comprador = (Comprador) o;
        return id != null && Objects.equals(id, comprador.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
