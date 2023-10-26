package net.mundomangas.backend.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.mundomangas.backend.domain.model.Produto;
import net.mundomangas.backend.domain.service.CadastroProdutoService;

@CrossOrigin("*")
@RestController
@RequestMapping("/produtos")
public class ProdutoController {
	
	@Autowired
	private CadastroProdutoService cadastro;
	
	@GetMapping
	public List<Produto> listar(@RequestParam("page") Integer page,
								 @RequestParam("order") String order) {
		
		return cadastro.listarPorPagina(page, order);
	}
	
//	@GetMapping("/{id}")
//	public Editora buscar(@PathVariable Long id) {
//		return cadastro.buscarOuFalhar(id);
//	}
//	
//	@GetMapping("/por-nome")
//	public List<Editora> buscarPorNome(@RequestParam("nome") String nome,
//										@RequestParam("page") Integer page,
//										@RequestParam("order") String order) {
//		
//		return cadastro.findByName(nome, page, order);
//	}
//	
//	@PostMapping
//	@ResponseStatus(HttpStatus.CREATED)
//	public Editora adicionar(@RequestBody Editora editora) {
//		return cadastro.salvar(editora);
//	}
//	
//	@PutMapping("/{id}")
//	public Editora atualizar(@PathVariable Long id,
//			@RequestBody Editora editora) {
//		
//		Editora editoraAtual = cadastro.buscarOuFalhar(id);
//		
//		BeanUtils.copyProperties(editora, editoraAtual, "id");
//		
//		return cadastro.salvar(editoraAtual);
//	}
//	
//	@DeleteMapping("/{id}")
//	@ResponseStatus(HttpStatus.NO_CONTENT)
//	public void deletar(@PathVariable Long id) {
//		cadastro.excluir(id);
//	}
}
