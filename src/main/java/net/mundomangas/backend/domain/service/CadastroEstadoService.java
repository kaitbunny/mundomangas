package net.mundomangas.backend.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import net.mundomangas.backend.domain.exception.AtributoDeEnderecoNaoEncontradoException;
import net.mundomangas.backend.domain.model.Estado;
import net.mundomangas.backend.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {
	private static final String ENTIDADE = "estado";
	private static final int ITENS_POR_PAGINA = 20;
	
	@Autowired
	private EstadoRepository repository;
	
	public PaginatedResponseService<Estado> listarPorPagina(Integer page, String order) {
		Pageable pageable = pageableBuilder(page, order);
		Page<Estado> result = repository.findAll(pageable);
		
		return responseBuilder(result, page);
	}
	
	public Estado buscarPorSigla(String sigla) {
		return repository.findBySigla(sigla).orElse(null);
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
