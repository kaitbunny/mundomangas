package net.mundomangas.backend.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.mundomangas.backend.domain.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	Page<Produto> findByNomeContaining(String nome, Pageable pageable);
	
	Page<Produto> findByCategorias_NomeContaining(@Param("nome") String nome, Pageable pageable);
	
	Page<Produto> findByCategorias_Id(@Param("id") Integer id, Pageable pageable);
}
