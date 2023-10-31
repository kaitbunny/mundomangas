package net.mundomangas.backend.domain.service;

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
	private static final int ITENS_POR_PAGINA = 20;
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
		catch (EmptyResultDataAccessException e) {
			throw new ProdutoNaoEncontradoException(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_PRODUTO_EM_USO, id));
		}
	}

	public PaginatedResponseService<Produto> listarPorPagina(Integer page, String order) {
		Pageable pageable = pageableBuilder(page, order);
		Page<Produto> result = repository.findAll(pageable);
		
		return responseBuilder(result, page);
	}

	public PaginatedResponseService<Produto> findByName(String nome, Integer page, String order) {
		Pageable pageable = pageableBuilder(page, order);
		Page<Produto> result = repository.findByNomeContaining(nome, pageable);
		
		return responseBuilder(result, page);
	}

	public PaginatedResponseService<Produto> findByCategoriaId(Integer id, Integer page, String order) {
		Pageable pageable = pageableBuilder(page, order);
		Page<Produto> result = repository.findByCategorias_Id(id, pageable);
		
		return responseBuilder(result, page);
	}

	public Produto buscarOuFalhar(Long id) {
		return repository.findById(id).orElseThrow(() -> new ProdutoNaoEncontradoException(id));
	}
	
	private PaginatedResponseService<Produto> responseBuilder(Page<Produto> result, Integer page) {
		return new PaginatedResponseService<>(
				result.getContent(), result.getTotalPages(),
				result.getTotalElements(), page);
	}
	
	private Pageable pageableBuilder(Integer page, String order) {
		 
		int pageNumber = page - 1;
		
		if (order.equalsIgnoreCase("OrderByTopSaleDESC")) {
			return PageRequest.of(pageNumber, ITENS_POR_PAGINA, Sort.by("totalVendido").descending());
		}
		else if (order.equalsIgnoreCase("OrderByReleaseDateDESC")) {
			return PageRequest.of(pageNumber, ITENS_POR_PAGINA, Sort.by("dataPublicacao").descending());
		}
		else if(order.equalsIgnoreCase("OrderByPriceDESC")) {
			return PageRequest.of(pageNumber, ITENS_POR_PAGINA, Sort.by("preco").descending());
		}
		else if(order.equalsIgnoreCase("OrderByPriceASC")) {
			return PageRequest.of(pageNumber, ITENS_POR_PAGINA, Sort.by("preco").ascending());
		}
		else if(order.equalsIgnoreCase("OrderByNameASC")) {
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
