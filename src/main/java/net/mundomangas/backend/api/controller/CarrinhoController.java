package net.mundomangas.backend.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import net.mundomangas.backend.domain.dto.AdicionarItemDTO;
import net.mundomangas.backend.domain.dto.AtualizarItemDTO;
import net.mundomangas.backend.domain.model.Produto;
import net.mundomangas.backend.domain.service.CadastroCarrinhoService;
import net.mundomangas.backend.domain.service.PaginatedResponseService;

@CrossOrigin("*")
@RestController
@RequestMapping("/carrinho")
public class CarrinhoController {

	@Autowired
	private CadastroCarrinhoService cadastro;
	
	@GetMapping
	public PaginatedResponseService<Produto> listar(@RequestParam("page") Integer page, Authentication authentication) {
		
		return cadastro.listarPorPagina(page, authentication);
	}
	
	@PostMapping
	public void adicionar(@RequestBody AdicionarItemDTO item, Authentication authentication) {
		cadastro.adicionarItem(item, authentication);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long id, Authentication authentication) {
		cadastro.deletar(id, authentication);
	}
	
	@PutMapping("/{id}")
	public void atualizar(@PathVariable Long id,
				@RequestBody AtualizarItemDTO quantidade, Authentication authentication) {
		
		cadastro.atualizar(id, quantidade, authentication);
	}
	
}