package net.mundomangas.backend.domain.exception;

public class CategoriaNaoEncontradaException extends EntidadeNaoEncontradaException {
	private static final long serialVersionUID = 1L;

	public CategoriaNaoEncontradaException(String msg) {
		super(msg);
	}
	
	public CategoriaNaoEncontradaException(Long id) {
		this(String.format("Não existe um cadastro de editora com código %d.", id));
	}
}
