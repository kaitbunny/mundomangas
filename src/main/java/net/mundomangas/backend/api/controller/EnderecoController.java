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
import net.mundomangas.backend.domain.model.Usuario;
import net.mundomangas.backend.domain.service.CadastroEnderecoService;
import net.mundomangas.backend.domain.service.PaginatedResponseService;
import net.mundomangas.backend.domain.service.UsuarioService;

@CrossOrigin("*")
@RestController
@RequestMapping("/enderecos-user")
public class EnderecoController {

	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	private CadastroEnderecoService cadastro;

	@GetMapping
	public PaginatedResponseService<EnderecoDTO> listar(@RequestParam("page") Integer page,
			Authentication authentication) {
		
		Usuario usuario = usuarioService.findUsuario(authentication);
		
		return cadastro.listarPorPagina(page, usuario);
	}

	@PostMapping
	public void adicionar(@RequestBody UsuarioEnderecoDTO endereco, Authentication authentication) {
		
		Usuario usuario = usuarioService.findUsuario(authentication);
		
		cadastro.salvar(usuario, endereco);
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
