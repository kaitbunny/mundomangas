package net.mundomangas.backend.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.mundomangas.backend.domain.model.Editora;
import net.mundomangas.backend.domain.service.CadastroEditoraService;

@RestController
@RequestMapping("/editoras")
public class EditoraController {
	
	@Autowired
	private CadastroEditoraService cadastro;
	
	@GetMapping
	private List<Editora> listar(@RequestParam("page") Integer page) {
		return cadastro.listarPorPagina(page);
	}
	
	@GetMapping("/{id}")
	private Editora buscar(@PathVariable Long id) {
		return cadastro.buscarOuFalhar(id);
	}
	
	@GetMapping("/por-nome")
	private List<Editora> buscarPorNome(@RequestParam("nome") String nome,
										@RequestParam("page") Integer page) {
		
		return cadastro.findByName(nome, page);
	}
	
}
