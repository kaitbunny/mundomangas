package net.mundomangas.backend.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.mundomangas.backend.domain.model.Editora;

@Repository
public interface EditoraRepository extends JpaRepository<Editora, Long> {
	
	List<Editora> findByNomeContaining(String nome);
	
}