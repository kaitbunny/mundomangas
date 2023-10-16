package net.mundomangas.backend.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
	RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
	ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio"),
	ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sistema"),
	ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso");
	
	private String title;
	private String uri;
	
	ProblemType(String path, String title){
		this.uri = "https://mundomangas.net" + path;
		this.title = title;
	}
	
}
