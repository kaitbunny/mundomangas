package net.mundomangas.backend.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.mundomangas.backend.domain.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	
	Page<Produto> findByNomeContaining(String nome, Pageable pageable);
	
	@Query("SELECT p FROM Produto p JOIN p.categorias c WHERE c.nome LIKE :nome")
	Page<Produto> findByCategoriaNome(@Param("nome") String nome, Pageable pageable);

}
