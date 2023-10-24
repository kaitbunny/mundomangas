package net.mundomangas.backend.api.controller;

import java.util.List;

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

import net.mundomangas.backend.domain.model.Categoria;
import net.mundomangas.backend.domain.service.CadastroCategoriaService;

@CrossOrigin("*")
@RestController
@RequestMapping("/categorias")
public class CategoriaController {
	
	@Autowired
	private CadastroCategoriaService cadastro;
	
	@GetMapping
	public List<Categoria> listar(@RequestParam("page") Integer page,
								 @RequestParam("order") String order) {
		
		return cadastro.listarPorPagina(page, order);
	}
	
	@GetMapping("/{id}")
	public Categoria buscar(@PathVariable Long id) {
		return cadastro.buscarOuFalhar(id);
	}
	
	@GetMapping("/por-nome")
	public List<Categoria> buscarPorNome(@RequestParam("nome") String nome,
										@RequestParam("page") Integer page,
										@RequestParam("order") String order) {
		
		return cadastro.findByName(nome, page, order);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Categoria adicionar(@RequestBody Categoria categoria) {
		return cadastro.salvar(categoria);
	}
	
	@PutMapping("/{id}")
	public Categoria atualizar(@PathVariable Long id,
			@RequestBody Categoria categoria) {
		
		Categoria categoriaAtual = cadastro.buscarOuFalhar(id);
		
		BeanUtils.copyProperties(categoria, categoriaAtual, "id");
		
		return cadastro.salvar(categoriaAtual);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long id) {
		cadastro.excluir(id);
	}
}