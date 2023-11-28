package net.mundomangas.backend.api.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import net.mundomangas.backend.domain.model.Logradouro;
import net.mundomangas.backend.domain.service.CadastroLogradouroService;
import net.mundomangas.backend.domain.service.PaginatedResponseService;

@CrossOrigin("*")
@RestController
@RequestMapping("/logradouros")
public class LogradouroController {
	
	@Autowired
	private CadastroLogradouroService cadastro;
	
	@GetMapping
	public PaginatedResponseService<Logradouro> listar(@RequestParam("page") Integer page,
			 @RequestParam("order") String order) {
		
		return cadastro.listarPorPagina(page, order);
	}
	
	@GetMapping("/{id}")
	public Logradouro buscar(@PathVariable Long id) {
		return cadastro.buscarOuFalhar(id);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Logradouro adicionar(@RequestBody Logradouro logradouro) {
		return cadastro.salvar(logradouro);
	}
	
	@PutMapping("/{id}")
	public Logradouro atualizar(@PathVariable Long id,
			@RequestBody Logradouro logradouro) {
		
		Logradouro logradouroAtual = cadastro.buscarOuFalhar(id);
		
		BeanUtils.copyProperties(logradouro, logradouroAtual, "id");
		
		return cadastro.salvar(logradouroAtual);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long id) {
		cadastro.excluir(id);
	}
}
