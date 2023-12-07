package net.mundomangas.backend.domain.dto;

import net.mundomangas.backend.domain.model.Endereco;

public record EnderecoDTO(Long id, String uf, String localidade,
		String bairro, String logradouro, String cep, String numero) {

	public EnderecoDTO(Endereco e) {
		this(
			e.getId(),
			e.getUf(),
			e.getLocalidade(),
			e.getBairro(),
			e.getLogradouro(),
			formatCep(e.getCep()),
			e.getNumero()
		);
	}
	
	public static String formatCep(String cep) {
		
		String responseCep = "";
		
		responseCep = cep.substring(0, 5) + "-" + cep.substring(5);
		
		return responseCep;
	}
	
}
