package net.mundomangas.backend.domain.model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

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
import net.mundomangas.backend.domain.exception.CpfInvalidoException;
import net.mundomangas.backend.domain.exception.EmailInvalidoException;

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
		if (this.permissao == PermissaoDeUsuario.ADMIN) {
			return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
		} else {
			return List.of(new SimpleGrantedAuthority("ROLE_USER"));
		}
	}

	public Usuario() {
	}

	public Usuario(String nome, String sobrenome, LocalDate dataNascimento, String cpf, String email, String senha,
			PermissaoDeUsuario permissao) {
		super();
		if (validaCPF(cpf)) {
			this.cpf = cpf;
		} else {
			throw new CpfInvalidoException("Cpf inválido. Por favor, digite um cpf válido.");
		}

		if (validaEmail(email)) {
			this.email = email;
		} else {
			throw new EmailInvalidoException("Email inválido. Por favor, digite um email válido.");
		}

		this.senha = senha;
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.dataNascimento = dataNascimento;
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

	private static boolean validaCPF(String cpf) {
		int i, j, soma, resto, dv1, dv2;

		int[] cpf_digitos = new int[11];

		for (int a = 0; a < 11; a++) {
			cpf_digitos[a] = Character.getNumericValue(cpf.charAt(a));
		}

		soma = 0;
		j = 10;

		for (i = 0; i < 9; i++) {
			soma += cpf_digitos[i] * j;
			j -= 1;
		}

		resto = soma % 11;

		if (resto < 2) {
			dv1 = 0;
		} else {
			dv1 = 11 - resto;
		}

		soma = 0;
		j = 11;

		for (i = 0; i < 10; i++) {
			soma += cpf_digitos[i] * j;
			j -= 1;
		}

		resto = soma % 11;

		if (resto < 2) {
			dv2 = 0;
		} else {
			dv2 = 11 - resto;
		}

		return cpf_digitos[9] == dv1 && cpf_digitos[10] == dv2;
	}

	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");

	private static boolean validaEmail(String email) {
		if (email == null) {
			return false;
		}
		return EMAIL_PATTERN.matcher(email).matches();
	}
}
