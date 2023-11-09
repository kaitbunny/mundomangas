package net.mundomangas.backend.domain.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import net.mundomangas.backend.domain.dto.AdicionarItemDTO;
import net.mundomangas.backend.domain.exception.EntidadeEmUsoException;
import net.mundomangas.backend.domain.exception.QuantidadeInvalidaException;
import net.mundomangas.backend.domain.model.Carrinho;
import net.mundomangas.backend.domain.model.Produto;
import net.mundomangas.backend.domain.model.Usuario;
import net.mundomangas.backend.domain.repository.CarrinhoRepository;

@Service
public class CadastroCarrinhoService {

	@Autowired
	CarrinhoRepository carrinhoRepository;
	
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
			
			carrinhoRepository.save(carrinho);
		}
		catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("O produto '%s' já está no seu carrinho!", produto.getNome()));
		}
	}
}
