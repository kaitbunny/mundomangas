package net.mundomangas.backend.domain.service;

import org.springframework.beans.factory.annotation.Autowired;

import net.mundomangas.backend.domain.exception.ProdutoNaoEncontradoException;
import net.mundomangas.backend.domain.model.Produto;
import net.mundomangas.backend.domain.repository.ProdutoRepository;

public class CadastroProdutoService {
	
	@Autowired
	private ProdutoRepository repository;
	
	public Produto buscarOuFalhar(Long id) {
		return repository.findById(id).orElseThrow(() ->
		new ProdutoNaoEncontradoException(id));
	}
	
}
