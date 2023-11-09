package net.mundomangas.backend.domain.dto;

import java.time.LocalDate;

import net.mundomangas.backend.domain.model.Usuario;

public record UserDTO(String email, String nome, String sobrenome,
		LocalDate dataNascimento, String cpf) {
	
	public UserDTO(Usuario u) {
		this(u.getEmail(), u.getNome(), u.getSobrenome(), u.getDataNascimento(), u.getCpf());
	}
}
