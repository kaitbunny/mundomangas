package net.mundomangas.backend.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import net.mundomangas.backend.domain.repository.UsuarioRepository;

@Service
public class AuthorizationService implements UserDetailsService {
	
	@Autowired
	UsuarioRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("LoadUserByUsername");
		return repository.findByEmail(username);
	}
	
}
