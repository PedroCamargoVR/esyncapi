package br.com.pedrocamargo.esync.modules.fornecedor.repository;

import br.com.pedrocamargo.esync.modules.fornecedor.model.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FornecedorRepository extends JpaRepository<Fornecedor,Long> {}
