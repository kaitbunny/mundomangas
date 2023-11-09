package net.mundomangas.backend.domain.exception;

public class QuantidadeInvalidaException extends NegocioException {
	private static final long serialVersionUID = 1L;

	public QuantidadeInvalidaException(String mensagem) {
		super(mensagem);
	}
}
