package br.com.pedrocamargo.esync.modules.usuario.repository;

import br.com.pedrocamargo.esync.modules.usuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
}
