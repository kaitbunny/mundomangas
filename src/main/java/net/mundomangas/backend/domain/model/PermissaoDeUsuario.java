package net.mundomangas.backend.domain.model;

public enum PermissaoDeUsuario {
	ADMIN("admin"),
	USER("user");
	
	private String permissao;
	
	PermissaoDeUsuario(String permissao) {
		this.permissao = permissao;
	}
	
	public String getPermissao() {
		return permissao;
	}
}
