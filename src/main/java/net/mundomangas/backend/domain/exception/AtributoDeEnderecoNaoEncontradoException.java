package net.mundomangas.backend.domain.exception;

public class AtributoDeEnderecoNaoEncontradoException extends EntidadeNaoEncontradaException {
	private static final long serialVersionUID = 1L;

	public AtributoDeEnderecoNaoEncontradoException(String msg) {
		super(msg);
	}
	
	public AtributoDeEnderecoNaoEncontradoException(String entidade, Long id) {
		this(String.format("Não existe um cadastro de %s com código %d.",entidade, id));
	}
	
}
