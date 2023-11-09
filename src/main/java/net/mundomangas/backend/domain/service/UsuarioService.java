package net.mundomangas.backend.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import net.mundomangas.backend.domain.dto.UserDTO;
import net.mundomangas.backend.domain.model.Usuario;
import net.mundomangas.backend.domain.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository repository;
	
	public UserDTO findUsuarioDTO(Authentication authentication) {
		return new UserDTO(repository.findUsuarioByEmail(authentication.getName()));
	}
	
	public Usuario findUsuario(Authentication authentication) {
		return repository.findUsuarioByEmail(authentication.getName());
	}
	
	public Long getUserId(Authentication authentication) {
		var user = repository.findUsuarioByEmail(authentication.getName());
		return user.getId();
	}
	
}
