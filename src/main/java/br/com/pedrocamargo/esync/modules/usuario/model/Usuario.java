package br.com.pedrocamargo.esync.modules.usuario.model;

import br.com.pedrocamargo.esync.modules.permissao.model.Permissao;
import jakarta.persistence.*;
import lombok.*;

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
}
