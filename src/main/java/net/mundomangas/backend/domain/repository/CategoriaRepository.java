package net.mundomangas.backend.domain.repository;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.mundomangas.backend.domain.model.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
	
	Page<Categoria> findByNomeContaining(String nome, Pageable pageable);
	
	@Query(value = "WITH RECURSIVE categoria_recursiva AS (" +
		    "SELECT c.id, c.nome, ccp.categoria_pai_id " +
		    "FROM categoria c " +
		    "LEFT JOIN categoria_categoria_pai ccp ON c.id = ccp.categoria_id " +
		    "WHERE c.nome = :nomeCategoria " +
		    "UNION ALL " +
		    "SELECT c.id, c.nome, ccp.categoria_pai_id " +
		    "FROM categoria c " +
		    "JOIN categoria_categoria_pai ccp ON c.id = ccp.categoria_id " +
		    "JOIN categoria_recursiva cr ON ccp.categoria_pai_id = cr.id" +
		") SELECT * FROM categoria_recursiva LIMIT :limit OFFSET :offset", nativeQuery = true)
		Set<Categoria> buscarPorCategoriaPai(@Param("nomeCategoria") String nomeCategoria,
				@Param("limit") int limit, @Param("offset") long offset);

	
}