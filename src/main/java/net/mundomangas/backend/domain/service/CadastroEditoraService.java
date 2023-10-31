package net.mundomangas.backend.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import net.mundomangas.backend.domain.exception.EditoraNaoEncontradaException;
import net.mundomangas.backend.domain.exception.EntidadeEmUsoException;
import net.mundomangas.backend.domain.model.Editora;
import net.mundomangas.backend.domain.repository.EditoraRepository;

@Service
public class CadastroEditoraService {
	private static final int ITENS_POR_PAGINA = 20;
	private static final String MSG_EDITORA_EM_USO = "Editora de código %d não pode ser removida, pois está em uso";
	
	@Autowired
	private EditoraRepository repository;
	
	public Editora salvar(Editora editora) {
		return repository.save(editora);
	}
	
	public void excluir(Long id) {
		try {
			repository.deleteById(id);
		}
		catch(EmptyResultDataAccessException e) {
			throw new EditoraNaoEncontradaException(id);
		}
		catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_EDITORA_EM_USO, id));
		}
	}
	
	public PaginatedResponseService<Editora> findByName(String nome, Integer page, String order) {
		Pageable pageable = pageableBuilder(page, order);
		Page<Editora> result = repository.findByNomeContaining(nome, pageable);
		
		return responseBuilder(result, page);
	}

	public PaginatedResponseService<Editora> listarPorPagina(Integer page, String order) {
		Pageable pageable = pageableBuilder(page, order);
		Page<Editora> result = repository.findAll(pageable);
		
		return responseBuilder(result, page);
	}
	
	public Editora buscarOuFalhar(Long id) {
		return repository.findById(id).orElseThrow(() ->
		new EditoraNaoEncontradaException(id));
	}
	
	private PaginatedResponseService<Editora> responseBuilder(Page<Editora> result, Integer page) {
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
