package br.com.pedrocamargo.esync.modules.usuario.model;

import br.com.pedrocamargo.esync.modules.permissao.model.Permissao;
import br.com.pedrocamargo.esync.modules.usuario.dto.UsuarioDTORequest;
import jakarta.persistence.*;
import lombok.*;

import java.util.Map;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_permissao")
    private Permissao permissao;
    private String nome;
    private String usuario;
    private String senha;
    private Boolean is_active;

    public Long getIdPermissao(){
        return this.permissao.getId();
    }

    public void update(UsuarioDTORequest usuarioRequest,Permissao permissao) {
        this.permissao = permissao;
        Map<String, Object> mapAttributes = usuarioRequest.mapAttributes();
        for(String keyAttribute : mapAttributes.keySet()){
            if(mapAttributes.get(keyAttribute) != null){
                setAttributeByNameString(keyAttribute,mapAttributes.get(keyAttribute));
            }
        }
    }

    private void setAttributeByNameString(String key, Object o){
        switch (key){
            case "nome":
                this.nome = (String) o;
                break;
            case "usuario":
                this.usuario = (String) o;
                break;
            case "senha":
                this.senha = (String) o;
                break;
        }
    }

    public void delete(){
        this.is_active = false;
    }
}
