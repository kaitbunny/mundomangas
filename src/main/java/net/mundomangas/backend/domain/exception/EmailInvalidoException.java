package net.mundomangas.backend.domain.exception;

public class EmailInvalidoException extends AtributoDeCadastroInvalidoException {
	private static final long serialVersionUID = 1L;

	public EmailInvalidoException(String mensagem) {
		super(mensagem);
	}
}
