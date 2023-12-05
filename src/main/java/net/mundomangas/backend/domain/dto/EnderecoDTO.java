package net.mundomangas.backend.domain.dto;

import net.mundomangas.backend.domain.model.Logradouro;

public record EnderecoDTO(Long id, String estado, String cidade, String bairro, String logradouro, String cep, String numero) {
	
	public EnderecoDTO(Logradouro log, String numero) {
		this(
			log.getId(),
			log.getBairro().getCidade().getEstado().getNome(),
			log.getBairro().getCidade().getNome(),
			log.getBairro().getNome(),
			log.getNome(),
			cepResponse(log.getCep()),
			numero
		);
	}
	
	public static String cepResponse(String cep) {
		String formatedCep;
		
		formatedCep = cep.substring(0, 5) + "-" + cep.substring(5);
		
		return formatedCep;
	}
	
}
