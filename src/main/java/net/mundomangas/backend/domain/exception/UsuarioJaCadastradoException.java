package net.mundomangas.backend.domain.exception;

public class UsuarioJaCadastradoException extends NegocioException {
	private static final long serialVersionUID = 1L;

	public UsuarioJaCadastradoException(String mensagem) {
		super(mensagem);
	}
}
