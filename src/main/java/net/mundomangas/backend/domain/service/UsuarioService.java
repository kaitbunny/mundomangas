package net.mundomangas.backend.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import net.mundomangas.backend.domain.dto.UserDTO;
import net.mundomangas.backend.domain.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository repository;
	
	public UserDTO findUsuario(Authentication authentication) {
		return new UserDTO(repository.findUsuarioByEmail(authentication.getName()));
	}
	
}
