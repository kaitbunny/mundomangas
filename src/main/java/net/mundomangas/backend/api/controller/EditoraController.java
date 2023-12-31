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

import net.mundomangas.backend.domain.model.Editora;
import net.mundomangas.backend.domain.service.CadastroEditoraService;
import net.mundomangas.backend.domain.service.PaginatedResponseService;

@CrossOrigin("*")
@RestController
@RequestMapping("/editoras")
public class EditoraController {
	
	@Autowired
	private CadastroEditoraService cadastro;
	
	@GetMapping
	public PaginatedResponseService<Editora> listar(@RequestParam("page") Integer page,
								 @RequestParam("order") String order) {
		
		return cadastro.listarPorPagina(page, order);
	}
	
	@GetMapping("/{id}")
	public Editora buscar(@PathVariable Long id) {
		return cadastro.buscarOuFalhar(id);
	}
	
	@GetMapping("/por-nome")
	public PaginatedResponseService<Editora> buscarPorNome(@RequestParam("nome") String nome,
										@RequestParam("page") Integer page,
										@RequestParam("order") String order) {
		
		return cadastro.findByName(nome, page, order);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Editora adicionar(@RequestBody Editora editora) {
		return cadastro.salvar(editora);
	}
	
	@PutMapping("/{id}")
	public Editora atualizar(@PathVariable Long id,
			@RequestBody Editora editora) {
		
		Editora editoraAtual = cadastro.buscarOuFalhar(id);
		
		BeanUtils.copyProperties(editora, editoraAtual, "id");
		
		return cadastro.salvar(editoraAtual);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long id) {
		cadastro.excluir(id);
	}
}
