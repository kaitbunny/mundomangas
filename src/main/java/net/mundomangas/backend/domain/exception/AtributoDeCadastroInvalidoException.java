package net.mundomangas.backend.domain.exception;

public abstract class AtributoDeCadastroInvalidoException extends NegocioException {
	private static final long serialVersionUID = 1L;

	public AtributoDeCadastroInvalidoException(String mensagem) {
		super(mensagem);
	}
}
