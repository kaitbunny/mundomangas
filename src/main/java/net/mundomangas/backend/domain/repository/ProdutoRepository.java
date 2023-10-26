package net.mundomangas.backend.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import net.mundomangas.backend.domain.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	
	Page<Produto> findByNomeContaining(String nome, Pageable pageable);
	
}
