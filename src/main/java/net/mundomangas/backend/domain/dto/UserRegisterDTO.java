package net.mundomangas.backend.domain.dto;

import java.time.LocalDate;

public record UserRegisterDTO(String email, String senha, String nome, String sobrenome,
		String cpf, LocalDate dataNascimento) {
}
