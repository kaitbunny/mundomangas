package net.mundomangas.backend.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import net.mundomangas.backend.domain.model.Editora;
import net.mundomangas.backend.domain.repository.EditoraRepository;

@Service
public class CadastroEditoraService {
	
	@Autowired
	EditoraRepository repository;
	
	public Editora salvar(Editora editora) {
		return repository.save(editora);
	}
	
	
	public Editora buscarOuFalhar(Long Id) {
		return repository.findById(Id).orElse(null);
	}


	public List<Editora> findByName(String nome) {
		List<Editora> list = repository.findByNomeContaining(nome);
		list.sort((n1, n2) -> n1.getNome().compareTo(n2.getNome()));
		return list;
	}


	public List<Editora> listarPorPagina(Integer page) {
		Sort sort = Sort.by("nome");
		Pageable pageable = PageRequest.of(page - 1, 20, sort);
		return repository.findAll(pageable).toList();
	}
}
