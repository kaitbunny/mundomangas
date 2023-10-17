package net.mundomangas.backend.domain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import net.mundomangas.backend.domain.exception.CategoriaNaoEncontradaException;
import net.mundomangas.backend.domain.exception.EntidadeEmUsoException;
import net.mundomangas.backend.domain.model.Categoria;
import net.mundomangas.backend.domain.repository.CategoriaRepository;

@Service
public class CadastroCategoriaService {
	
	private static final String MSG_CATEGORIA_EM_USO = "Categoria de código %d não pode ser removida, pois está em uso";
	@Autowired
	CategoriaRepository repository;
	
	public Categoria salvar(Categoria categoria) {
		return repository.save(categoria);
	}
	
	public void excluir(Long id) {
		try {
			repository.deleteById(id);
		}
		catch(EmptyResultDataAccessException e) {
			throw new CategoriaNaoEncontradaException(id);
		}
		catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_CATEGORIA_EM_USO, id));
		}
	}
	
	public Categoria buscarOuFalhar(Long id) {
		return repository.findById(id).orElseThrow(() ->
		new CategoriaNaoEncontradaException(id));
	}

	public List<Categoria> findByName(String nome, Integer page, String order) {
		Pageable pageable = pageableBuilder(page, order);
		
		Page<Categoria> list = repository.findByNomeContaining(nome, pageable);
		return list.toList();
	}

	public List<Categoria> listarPorPagina(Integer page, String order) {
		Pageable pageable = pageableBuilder(page, order);
		
		return repository.findAll(pageable).toList();
	}
	
	public List<Categoria> listarPorCategoriaPai(String nomeCategoria, Integer page) {
		Pageable pageable = PageRequest.of(page - 1, 20);
		Set<Categoria> categorias = repository.buscarPorCategoriaPai(
				nomeCategoria, pageable.getPageSize(), pageable.getOffset());
		List<Categoria> categoriasList = new ArrayList<>(categorias);
		
		return categoriasList;
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
