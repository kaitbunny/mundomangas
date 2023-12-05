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
import net.mundomangas.backend.domain.model.Cidade;
import net.mundomangas.backend.domain.repository.CidadeRepository;

@Service
public class CadastroCidadeService {
	private static final String ENTIDADE = "cidade";
	private static final int ITENS_POR_PAGINA = 20;
	private static final String MSG_CIDADE_EM_USO = "Cidade de código %d não pode ser removida, pois está em uso";
	
	@Autowired
	private CidadeRepository repository;
	
	public Cidade salvar(Cidade cidade) {
		return repository.save(cidade);
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
					String.format(MSG_CIDADE_EM_USO, id));
		}
	}

	public PaginatedResponseService<Cidade> listarPorPagina(Integer page, String order) {
		Pageable pageable = pageableBuilder(page, order);
		Page<Cidade> result = repository.findAll(pageable);
		
		return responseBuilder(result, page);
	}
	
	public Cidade buscarPorNome(String nome, Long estadoId) {
		return repository.findByNomeAndEstado_Id(nome, estadoId).orElse(null);
	}
	
	public Cidade buscarOuFalhar(Long id) {
		return repository.findById(id).orElseThrow(() ->
		new AtributoDeEnderecoNaoEncontradoException(ENTIDADE, id));
	}
	
	private PaginatedResponseService<Cidade> responseBuilder(Page<Cidade> result, Integer page) {
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
