package net.mundomangas.backend.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import net.mundomangas.backend.domain.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	UserDetails findByEmail(String email);
	UserDetails findByCpf(String cpf);
}
