package net.mundomangas.backend.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import net.mundomangas.backend.domain.exception.AtributoDeEnderecoNaoEncontradoException;
import net.mundomangas.backend.domain.model.UsuarioEndereco;
import net.mundomangas.backend.domain.repository.UsuarioEnderecoRepository;

@Service
public class UsuarioEnderecoService {
	private static final String ENTIDADE = "endereco de usuario";
	private static final int ITENS_POR_PAGINA = 20;
	
	@Autowired
	private UsuarioEnderecoRepository repository;
	
	public UsuarioEndereco salvar(UsuarioEndereco usuarioEndereco) {
		return repository.save(usuarioEndereco);
	}
	
	public void excluir(Long id) {
		try {
			repository.deleteById(id);
		}
		catch(EmptyResultDataAccessException e) {
			throw new AtributoDeEnderecoNaoEncontradoException(ENTIDADE, id);
		}
	}

	public PaginatedResponseService<UsuarioEndereco> listarPorPagina(Integer page, String order) {
		Pageable pageable = pageableBuilder(page, order);
		Page<UsuarioEndereco> result = repository.findAll(pageable);
		
		return responseBuilder(result, page);
	}
	
	public UsuarioEndereco buscarOuFalhar(Long id) {
		return repository.findById(id).orElseThrow(() ->
		new AtributoDeEnderecoNaoEncontradoException(ENTIDADE, id));
	}
	
	private PaginatedResponseService<UsuarioEndereco> responseBuilder(Page<UsuarioEndereco> result, Integer page) {
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
