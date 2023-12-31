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

import net.mundomangas.backend.domain.model.Produto;
import net.mundomangas.backend.domain.service.CadastroProdutoService;
import net.mundomangas.backend.domain.service.PaginatedResponseService;

@CrossOrigin("*")
@RestController
@RequestMapping("/produtos")
public class ProdutoController {
	
	@Autowired
	private CadastroProdutoService cadastro;
	
	@GetMapping
	public PaginatedResponseService<Produto> listar(@RequestParam("page") Integer page,
								 @RequestParam("order") String order) {
		
		return cadastro.listarPorPagina(page, order);
	}
	
	@GetMapping("/{id}")
	public Produto buscar(@PathVariable Long id) {
		return cadastro.buscarOuFalhar(id);
	}
	
	@GetMapping("/por-nome")
	public PaginatedResponseService<Produto> listarPorNome(@RequestParam("nome") String nome,
								@RequestParam("page") Integer page,
								@RequestParam("order") String order) {
		return cadastro.findByName(nome, page, order);
	}
	
	@GetMapping("/por-categoria-id")
	public PaginatedResponseService<Produto> listarPorCategoria(@RequestParam("id") Integer id,
											@RequestParam("page") Integer page,
											@RequestParam("order") String order) {
		return cadastro.findByCategoriaId(id, page, order);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Produto adicionar(@RequestBody Produto produto) {
		return cadastro.salvar(produto);
	}
	
	@PutMapping("/{id}")
	public Produto atualizar(@PathVariable Long id,
			@RequestBody Produto produto) {
		
		Produto produtoAtual = cadastro.buscarOuFalhar(id);
		
		BeanUtils.copyProperties(produto, produtoAtual, "id");
		
		return cadastro.salvar(produtoAtual);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long id) {
		cadastro.excluir(id);
	}
}