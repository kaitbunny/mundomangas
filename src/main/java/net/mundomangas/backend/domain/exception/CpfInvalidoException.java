package net.mundomangas.backend.domain.exception;

public class CpfInvalidoException extends AtributoDeCadastroInvalidoException {
	private static final long serialVersionUID = 1L;

	public CpfInvalidoException(String mensagem) {
		super(mensagem);
	}
}
