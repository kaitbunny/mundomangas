package net.mundomangas.backend.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import net.mundomangas.backend.domain.exception.AtributoDeEnderecoNaoEncontradoException;
import net.mundomangas.backend.domain.exception.EntidadeEmUsoException;
import net.mundomangas.backend.domain.model.Estado;
import net.mundomangas.backend.domain.repository.EstadoRepository;

@Service
public class EstadoService {
	private static final String ENTIDADE = "estado";
	private static final int ITENS_POR_PAGINA = 20;
	private static final String MSG_ESTADO_EM_USO = "Estado de código %d não pode ser removido, pois está em uso";
	
	@Autowired
	private EstadoRepository repository;
	
	public Estado salvar(Estado estado) {
		return repository.save(estado);
	}
	
	public void excluir(Long id) {
		try {
			repository.deleteById(id);
		}
		catch(EmptyResultDataAccessException e) {
			throw new AtributoDeEnderecoNaoEncontradoException(ENTIDADE, id);
		}
		catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_ESTADO_EM_USO, id));
		}
	}

	public PaginatedResponseService<Estado> listarPorPagina(Integer page, String order) {
		Pageable pageable = pageableBuilder(page, order);
		Page<Estado> result = repository.findAll(pageable);
		
		return responseBuilder(result, page);
	}
	
	public Estado buscarOuFalhar(Long id) {
		return repository.findById(id).orElseThrow(() ->
		new AtributoDeEnderecoNaoEncontradoException(ENTIDADE, id));
	}
	
	private PaginatedResponseService<Estado> responseBuilder(Page<Estado> result, Integer page) {
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
