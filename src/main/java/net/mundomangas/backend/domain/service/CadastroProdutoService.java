package net.mundomangas.backend.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import net.mundomangas.backend.domain.exception.EntidadeEmUsoException;
import net.mundomangas.backend.domain.exception.ProdutoNaoEncontradoException;
import net.mundomangas.backend.domain.model.Produto;
import net.mundomangas.backend.domain.repository.ProdutoRepository;

@Service
public class CadastroProdutoService {
	private static final String MSG_PRODUTO_EM_USO = "Produto de código %d não pode ser removido, pois está em uso";
	
	@Autowired
	private ProdutoRepository repository;
	
	public Produto salvar(Produto produto) {
		return repository.save(produto);
	}
	
	public void excluir(Long id) {
		try {
			repository.deleteById(id);
		}
		catch(EmptyResultDataAccessException e) {
			throw new ProdutoNaoEncontradoException(id);
		}
		catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_PRODUTO_EM_USO, id));
		}
	}
	
	public List<Produto> listarPorPagina(Integer page, String order) {
		Pageable pageable = pageableBuilder(page, order);
		
		return repository.findAll(pageable).toList();
	}
	
	public List<Produto> findByName(String nome, Integer page, String order) {
		Pageable pageable = pageableBuilder(page, order);
		
		Page<Produto> list = repository.findByNomeContaining(nome, pageable);
		return list.toList();
	}
	
	public List<Produto> findByCategoriaNome(String nome, Integer page, String order) {
		Pageable pageable = pageableBuilder(page, order);
		
		Page<Produto> list = repository.findByCategoriaNome(nome, pageable);
		return list.toList();
	}
	
	public Produto buscarOuFalhar(Long id) {
		return repository.findById(id).orElseThrow(() ->
		new ProdutoNaoEncontradoException(id));
	}
	
	@SuppressWarnings("unused")
	private Pageable pageableBuilder(Integer page, String order) {
		Sort sort = null;
		Pageable pageable = null;
		
		if(order.equalsIgnoreCase("asc")) {
			sort = Sort.by("nome").ascending();
			return pageable = PageRequest.of(page - 1, 20, sort);
		}
		else if(order.equalsIgnoreCase("desc")) {
			sort = Sort.by("nome").descending();
			return pageable = PageRequest.of(page - 1, 20, sort);
		}
		else {
			return pageable = PageRequest.of(page - 1, 20);
		}
	}
}
