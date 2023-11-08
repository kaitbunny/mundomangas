package net.mundomangas.backend.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.mundomangas.backend.domain.model.Editora;

@Repository
public interface EditoraRepository extends JpaRepository<Editora, Long> {
	
	Page<Editora> findByNomeContaining(String nome, Pageable pageable);
	
}