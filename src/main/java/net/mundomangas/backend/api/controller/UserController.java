package net.mundomangas.backend.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.mundomangas.backend.domain.dto.UserDTO;
import net.mundomangas.backend.domain.service.UsuarioService;

@CrossOrigin("*")
@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UsuarioService cadastro;
	
	@GetMapping
	public UserDTO exibir(Authentication authentication) {
		return cadastro.findUsuario(authentication);
	}
	
}
