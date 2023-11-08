package net.mundomangas.backend.api.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.mundomangas.backend.domain.dto.AuthenticationDTO;
import net.mundomangas.backend.domain.dto.LoginResponseDTO;
import net.mundomangas.backend.domain.dto.UserRegisterDTO;
import net.mundomangas.backend.domain.exception.UsuarioJaCadastradoException;
import net.mundomangas.backend.domain.model.PermissaoDeUsuario;
import net.mundomangas.backend.domain.model.Usuario;
import net.mundomangas.backend.domain.repository.UsuarioRepository;
import net.mundomangas.backend.domain.service.TokenService;

@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthenticationDTO data) {
		var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
		var auth = this.authenticationManager.authenticate(usernamePassword);
		
		var token = tokenService.generateToken((Usuario) auth.getPrincipal());
		
		System.out.println("Login" + auth.getPrincipal());
		return ResponseEntity.ok(new LoginResponseDTO(token));
	}
	
	@PostMapping("/admin-register")
	public ResponseEntity<?> adminRegister(@RequestBody UserRegisterDTO data) {
		var permissao = PermissaoDeUsuario.ADMIN;
		
		Usuario novoUsuario = criarUsuario(data, permissao);
		this.repository.save(novoUsuario);
		
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/user-register")
	public ResponseEntity<?> userRegister(@RequestBody UserRegisterDTO data) {
		var permissao = PermissaoDeUsuario.USER;
		
		Usuario novoUsuario = criarUsuario(data, permissao);
		this.repository.save(novoUsuario);
		
		return ResponseEntity.ok().build();
	}
	
	private Usuario criarUsuario(UserRegisterDTO data, PermissaoDeUsuario permissao) {
		if(this.repository.findByEmail(data.email()) != null) {
			throw new UsuarioJaCadastradoException("Esse email já está cadastrado em nosso sistema");
		}
		if(this.repository.findByCpf(data.cpf()) != null) {
			throw new UsuarioJaCadastradoException("Esse cpf já está cadastrado em nosso sistema");
		}
		
		String encryptedPassword = new BCryptPasswordEncoder().encode(data.senha());
		
		return new Usuario(data.nome(), data.sobrenome(), data.dataNascimento(),
				data.cpf(), data.email(), encryptedPassword, permissao);
	}
	
}
