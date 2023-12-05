package net.mundomangas.backend.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.mundomangas.backend.domain.model.Estado;
import net.mundomangas.backend.domain.service.CadastroEstadoService;
import net.mundomangas.backend.domain.service.PaginatedResponseService;

@CrossOrigin("*")
@RestController
@RequestMapping("/estados")
public class EstadoController {
	
	@Autowired
	private CadastroEstadoService cadastro;
	
	@GetMapping
	public PaginatedResponseService<Estado> listar(@RequestParam("page") Integer page,
			 @RequestParam("order") String order) {
		
		return cadastro.listarPorPagina(page, order);
	}
	
	@GetMapping("/{id}")
	public Estado buscar(@PathVariable Long id) {
		return cadastro.buscarOuFalhar(id);
	}
}
