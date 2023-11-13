package net.mundomangas.backend.domain.exception;

public class CarrinhoNaoEncontradoException extends EntidadeNaoEncontradaException {
	private static final long serialVersionUID = 1L;

	public CarrinhoNaoEncontradoException(String msg) {
		super(msg);
	}
	
	public CarrinhoNaoEncontradoException(Long id) {
		this(String.format("Não existe um cadastro de carrinho com código %d.", id));
	}
}
