package net.mundomangas.backend.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
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
}
