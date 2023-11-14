package net.mundomangas.backend.domain.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import net.mundomangas.backend.domain.dto.AdicionarItemDTO;
import net.mundomangas.backend.domain.dto.AtualizarItemDTO;
import net.mundomangas.backend.domain.dto.ItemCarrinhoDTO;
import net.mundomangas.backend.domain.dto.ListarCarrinhoDTO;
import net.mundomangas.backend.domain.exception.CarrinhoNaoEncontradoException;
import net.mundomangas.backend.domain.exception.EntidadeEmUsoException;
import net.mundomangas.backend.domain.exception.QuantidadeInvalidaException;
import net.mundomangas.backend.domain.model.Carrinho;
import net.mundomangas.backend.domain.model.Produto;
import net.mundomangas.backend.domain.model.Usuario;
import net.mundomangas.backend.domain.repository.CarrinhoRepository;

@Service
public class CadastroCarrinhoService {
	
	private static final int ITENS_POR_PAGINA = 20;
	
	@Autowired
	CarrinhoRepository repository;

	@Autowired
	UsuarioService usuarioService;

	@Autowired
	CadastroProdutoService produtoService;
	
	public void adicionarItem(AdicionarItemDTO item, Authentication authentication) {
		Produto produto = produtoService.buscarOuFalhar(item.id());

		if (item.quantidade() < 0 || item.quantidade() > produto.getEstoque()) {
			throw new QuantidadeInvalidaException(
					String.format("A quantidade do produto '%s' deve ser maior que zero " + "e menor ou igual a %d",
							produto.getNome(), produto.getEstoque()));
		}
		try {
			Usuario usuario = usuarioService.findUsuario(authentication);
			Integer quantidade = item.quantidade();
			LocalDate dataCriacao = LocalDate.now();

			Carrinho carrinho = new Carrinho();

			carrinho.setProduto(produto);
			carrinho.setUsuario(usuario);
			carrinho.setQuantidade(quantidade);
			carrinho.setDataCriacao(dataCriacao);

			repository.save(carrinho);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("O produto '%s' já está no seu carrinho!", produto.getNome()));
		}
	}

	public void deletar(Long id, Authentication authentication) {
		try {
			var carrinho = buscarOuFalhar(id);
			var usuario = usuarioService.findUsuario(authentication);
			if (!carrinho.getUsuario().equals(usuario)) {
				throw new CarrinhoNaoEncontradoException("Esse carrinho não pertence ao seu usuario");
			}

			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new CarrinhoNaoEncontradoException(id);
		}
	}

	public Carrinho buscarOuFalhar(Long id) {
		return repository.findById(id).orElseThrow(() -> new CarrinhoNaoEncontradoException(id));
	}

	public void atualizar(Long id, AtualizarItemDTO item, Authentication authentication) {
		var carrinho = buscarOuFalhar(id);
		var usuario = usuarioService.findUsuario(authentication);
		var produto = carrinho.getProduto();
		
		if (!carrinho.getUsuario().equals(usuario)) {
			throw new CarrinhoNaoEncontradoException("Esse carrinho não pertence ao seu usuario");
		}
		
		if (item.quantidade() == 0) {
			deletar(id, authentication);
			return;
		}
		
		if (item.quantidade() < 1 || item.quantidade() > produto.getEstoque()) {
			throw new QuantidadeInvalidaException(
					String.format("A quantidade do produto '%s' deve ser maior que zero " + "e menor ou igual a %d",
							produto.getNome(), produto.getEstoque()));
		}
		
		carrinho.setQuantidade(item.quantidade());

		var carrinhoAtual = buscarOuFalhar(id);
		BeanUtils.copyProperties(carrinho, carrinhoAtual, "id");
		
		repository.save(carrinhoAtual);
	}
	
	public ListarCarrinhoDTO listarPorPagina(Integer page, Authentication authentication) {
		var usuario = usuarioService.findUsuario(authentication);
		Pageable pageable = pageableBuilder(page);
		
		Page<Carrinho> carrinho = repository.findByUsuario_Id(usuario.getId(), pageable);
		
		Page<ItemCarrinhoDTO> result = carrinho.map(p -> new ItemCarrinhoDTO(p.getProduto(), p.getQuantidade(), p.getSubtotal()));

		return responseBuilder(result, page);
	}
	
	private ListarCarrinhoDTO responseBuilder(Page<ItemCarrinhoDTO> result, Integer page) {
		BigDecimal total = result.stream()
									.map(ItemCarrinhoDTO::subtotal)
									.reduce(BigDecimal.ZERO, BigDecimal::add);
		
		return new ListarCarrinhoDTO(result.getContent(), result.getTotalPages(), result.getTotalElements(),
				page, total);
	}
	
	private Pageable pageableBuilder(Integer page) {

		int pageNumber = page - 1;

		return PageRequest.of(pageNumber, ITENS_POR_PAGINA, Sort.by("dataCriacao").descending());
		
	}
}
