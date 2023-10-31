package net.mundomangas.backend.domain.service;

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
	private static final int ITENS_POR_PAGINA = 20;
	private static final String MSG_CATEGORIA_EM_USO = "Categoria de código %d não pode ser removida, pois está em uso";
	
	@Autowired
	private CategoriaRepository repository;
	
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
	
	public PaginatedResponseService<Categoria> findByName(String nome, Integer page, String order) {
		Pageable pageable = pageableBuilder(page, order);
		Page<Categoria> result = repository.findByNomeContaining(nome, pageable);
		
		return responseBuilder(result, page);
	}

	public PaginatedResponseService<Categoria> listarPorPagina(Integer page, String order) {
		Pageable pageable = pageableBuilder(page, order);
		Page<Categoria> result = repository.findAll(pageable);
		
		return responseBuilder(result, page);
	}
	
	private PaginatedResponseService<Categoria> responseBuilder(Page<Categoria> result, Integer page) {
		return new PaginatedResponseService<>(
				result.getContent(), result.getTotalPages(),
				result.getTotalElements(), page);
	}
	
	private Pageable pageableBuilder(Integer page, String order) {
		int pageNumber = page - 1;
		
		if(order.equalsIgnoreCase("OrderByNameASC")) {
			return PageRequest.of(pageNumber, ITENS_POR_PAGINA, Sort.by("nome").ascending());
		}
		else if(order.equalsIgnoreCase("OrderByNameDESC")) {
			return PageRequest.of(pageNumber, ITENS_POR_PAGINA, Sort.by("nome").descending());
		}
		else {
			return PageRequest.of(pageNumber, ITENS_POR_PAGINA);
		}
	}
	
}
