package net.mundomangas.backend.domain.exception;

public class UsuarioNaoExisteException extends NegocioException {
	private static final long serialVersionUID = 1L;

	public UsuarioNaoExisteException(String mensagem) {
		super(mensagem);
	}
}
