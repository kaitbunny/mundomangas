package net.mundomangas.backend.domain.exception;

public class CepInvalidoException extends AtributoDeCadastroInvalidoException {
	private static final long serialVersionUID = 1L;

	public CepInvalidoException(String cep) {
		super(String.format("CEP: '%s' não existe, por favor informe um CEP válido!", cep));
	}
}
