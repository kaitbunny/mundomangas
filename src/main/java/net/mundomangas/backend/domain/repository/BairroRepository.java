package net.mundomangas.backend.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.mundomangas.backend.domain.model.Bairro;

@Repository
public interface BairroRepository extends JpaRepository<Bairro, Long> {

	Optional<Bairro> findByNomeAndCidade_Id(String bairro, Long id);

}
