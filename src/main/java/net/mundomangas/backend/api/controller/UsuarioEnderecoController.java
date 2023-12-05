package net.mundomangas.backend.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.mundomangas.backend.domain.dto.EnderecoDTO;
import net.mundomangas.backend.domain.dto.UsuarioEnderecoDTO;
import net.mundomangas.backend.domain.service.CadastroUsuarioEnderecoService;
import net.mundomangas.backend.domain.service.PaginatedResponseService;

@CrossOrigin("*")
@RestController
@RequestMapping("/enderecos-user")
public class UsuarioEnderecoController {

	@Autowired
	private CadastroUsuarioEnderecoService cadastro;

	@GetMapping
	public PaginatedResponseService<EnderecoDTO> listar(@RequestParam("page") Integer page,
			Authentication authentication) {

		return cadastro.listarPorPagina(page, authentication);
	}

	@PostMapping
	public void adicionar(@RequestBody UsuarioEnderecoDTO endereco, Authentication authentication) {
		cadastro.salvar(authentication, endereco);
	}
	/*
	 * @DeleteMapping("/{id}")
	 * 
	 * @ResponseStatus(HttpStatus.NO_CONTENT) public void deletar(@PathVariable Long
	 * id, Authentication authentication) { cadastro.deletar(id, authentication); }
	 * 
	 * @PutMapping("/{id}") public void atualizar(@PathVariable Long id,
	 * 
	 * @RequestBody AtualizarItemDTO quantidade, Authentication authentication) {
	 * 
	 * cadastro.atualizar(id, quantidade, authentication); }
	 */
}
