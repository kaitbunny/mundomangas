package net.mundomangas.backend.domain.model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Usuario implements UserDetails {
	private static final long serialVersionUID = 1L;

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String senha;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false)
	private String sobrenome;
	
	@Column(nullable = false)
	private LocalDate dataNascimento;
	
	@Column(nullable = false)
	private String cpf;
	
	@Enumerated(EnumType.STRING)
	private PermissaoDeUsuario permissao;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if(this.permissao == PermissaoDeUsuario.ADMIN) {
			return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		else {
			return List.of(new SimpleGrantedAuthority("ROLE_USER"));
		}
	}
	
	public Usuario() {
	}
	
	public Usuario(String nome, String sobrenome, LocalDate dataNascimento, String cpf, String email, String senha,
			PermissaoDeUsuario permissao) {
		super();
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.dataNascimento = dataNascimento;
		this.cpf = cpf;
		this.email = email;
		this.senha = senha;
		this.permissao = permissao;
	}
	
	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
