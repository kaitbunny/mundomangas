package net.mundomangas.backend.domain.dto;

import java.math.BigDecimal;

import net.mundomangas.backend.domain.model.Produto;

public record ItemCarrinhoDTO(Long id, String nome, String uriFoto, BigDecimal preco, Integer quantidade, BigDecimal subtotal, Boolean ativo) {
	
	public ItemCarrinhoDTO(Produto p, Integer quantidade, BigDecimal subtotal) {
		this(p.getId(), p.getNome(), p.getUriFoto(), p.getPreco(), quantidade, subtotal, p.getAtivo());
	}
}
