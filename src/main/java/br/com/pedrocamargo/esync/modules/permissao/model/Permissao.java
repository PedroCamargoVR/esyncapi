package br.com.pedrocamargo.esync.modules.permissao.model;

import br.com.pedrocamargo.esync.modules.permissao.dto.PermissaoDTORequest;
import br.com.pedrocamargo.esync.modules.usuario.model.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "permissao")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Permissao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;
    @OneToMany
    @JoinColumn(name = "id_permissao")
    private List<Usuario> usuarios;

    public Permissao (String descricao){
        this.descricao = descricao;
    }

    public void update(PermissaoDTORequest permissaoRequest) {
        this.descricao = permissaoRequest.descricao();
    }
}
