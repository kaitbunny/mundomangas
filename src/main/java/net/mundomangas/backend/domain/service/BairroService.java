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
import net.mundomangas.backend.domain.model.Bairro;
import net.mundomangas.backend.domain.repository.BairroRepository;

@Service
public class BairroService {
	private static final String ENTIDADE = "bairro";
	private static final int ITENS_POR_PAGINA = 20;
	private static final String MSG_BAIRRO_EM_USO = "Bairro de código %d não pode ser removido, pois está em uso";
	
	@Autowired
	private BairroRepository repository;
	
	public Bairro salvar(Bairro bairro) {
		return repository.save(bairro);
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
					String.format(MSG_BAIRRO_EM_USO, id));
		}
	}

	public PaginatedResponseService<Bairro> listarPorPagina(Integer page, String order) {
		Pageable pageable = pageableBuilder(page, order);
		Page<Bairro> result = repository.findAll(pageable);
		
		return responseBuilder(result, page);
	}
	
	public Bairro buscarOuFalhar(Long id) {
		return repository.findById(id).orElseThrow(() ->
		new AtributoDeEnderecoNaoEncontradoException(ENTIDADE, id));
	}
	
	private PaginatedResponseService<Bairro> responseBuilder(Page<Bairro> result, Integer page) {
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
