package net.mundomangas.backend.domain.exception;

public class EditoraNaoEncontradaException extends EntidadeNaoEncontradaException {
	private static final long serialVersionUID = 1L;

	public EditoraNaoEncontradaException(String msg) {
		super(msg);
	}
	
	public EditoraNaoEncontradaException(Long id) {
		this(String.format("Não existe um cadastro de editora com código %d", id));
	}
	
}
