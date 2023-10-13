package net.mundomangas.backend.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.mundomangas.backend.domain.model.Editora;
import net.mundomangas.backend.domain.repository.EditoraRepository;
import net.mundomangas.backend.domain.service.CadastroEditoraService;

@RestController
@RequestMapping("/editoras")
public class EditoraController {
	
	@Autowired
	private EditoraRepository repository;
	
	@Autowired
	private CadastroEditoraService cadastro;
	
	@GetMapping("/pagina/{page}")
	private List<Editora> listar(@PathVariable Integer page) {
		Sort sort = Sort.by("nome");
		Pageable pageable = PageRequest.of(page - 1, 20, sort);
		return repository.findAll(pageable).toList();
	}
	
	@GetMapping("/{id}")
	private Editora buscar(@PathVariable Long id) {
		return cadastro.buscarOuFalhar(id);
	}
	
}
