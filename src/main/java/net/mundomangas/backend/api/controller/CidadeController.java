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

import net.mundomangas.backend.domain.model.Cidade;
import net.mundomangas.backend.domain.service.CadastroCidadeService;
import net.mundomangas.backend.domain.service.PaginatedResponseService;

@CrossOrigin("*")
@RestController
@RequestMapping("/cidades")
public class CidadeController {
	
	@Autowired
	private CadastroCidadeService cadastro;
	
	@GetMapping
	public PaginatedResponseService<Cidade> listar(@RequestParam("page") Integer page,
			 @RequestParam("order") String order) {
		
		return cadastro.listarPorPagina(page, order);
	}
	
	@GetMapping("/{id}")
	public Cidade buscar(@PathVariable Long id) {
		return cadastro.buscarOuFalhar(id);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cidade adicionar(@RequestBody Cidade cidade) {
		return cadastro.salvar(cidade);
	}
	
	@PutMapping("/{id}")
	public Cidade atualizar(@PathVariable Long id,
			@RequestBody Cidade cidade) {
		
		Cidade cidadeAtual = cadastro.buscarOuFalhar(id);
		
		BeanUtils.copyProperties(cidade, cidadeAtual, "id");
		
		return cadastro.salvar(cidadeAtual);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long id) {
		cadastro.excluir(id);
	}
}
