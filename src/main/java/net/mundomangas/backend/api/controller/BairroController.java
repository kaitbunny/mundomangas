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

import net.mundomangas.backend.domain.model.Bairro;
import net.mundomangas.backend.domain.service.CadastroBairroService;
import net.mundomangas.backend.domain.service.PaginatedResponseService;

@CrossOrigin("*")
@RestController
@RequestMapping("/bairros")
public class BairroController {
	
	@Autowired
	private CadastroBairroService cadastro;
	
	@GetMapping
	public PaginatedResponseService<Bairro> listar(@RequestParam("page") Integer page,
			 @RequestParam("order") String order) {
		
		return cadastro.listarPorPagina(page, order);
	}
	
	@GetMapping("/{id}")
	public Bairro buscar(@PathVariable Long id) {
		return cadastro.buscarOuFalhar(id);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Bairro adicionar(@RequestBody Bairro bairro) {
		return cadastro.salvar(bairro);
	}
	
	@PutMapping("/{id}")
	public Bairro atualizar(@PathVariable Long id,
			@RequestBody Bairro bairro) {
		
		Bairro bairroAtual = cadastro.buscarOuFalhar(id);
		
		BeanUtils.copyProperties(bairro, bairroAtual, "id");
		
		return cadastro.salvar(bairroAtual);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long id) {
		cadastro.excluir(id);
	}
}
