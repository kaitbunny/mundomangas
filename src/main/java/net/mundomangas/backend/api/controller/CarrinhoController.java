package net.mundomangas.backend.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.mundomangas.backend.domain.dto.AdicionarItemDTO;
import net.mundomangas.backend.domain.service.CadastroCarrinhoService;

@CrossOrigin("*")
@RestController
@RequestMapping("/carrinho")
public class CarrinhoController {

	@Autowired
	private CadastroCarrinhoService cadastro;

	@PostMapping
	public void adicionar(@RequestBody AdicionarItemDTO item, Authentication authentication) {
		cadastro.adicionarItem(item, authentication);
	}
	
}