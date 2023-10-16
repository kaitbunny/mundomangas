package net.mundomangas.backend.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import net.mundomangas.backend.domain.exception.EditoraNaoEncontradaException;
import net.mundomangas.backend.domain.exception.EntidadeEmUsoException;
import net.mundomangas.backend.domain.model.Editora;
import net.mundomangas.backend.domain.repository.EditoraRepository;

@Service
public class CadastroEditoraService {
	
	private static final String MSG_EDITORA_EM_USO = "Editora de código %d não pode ser removida, pois está em uso";
	@Autowired
	EditoraRepository repository;
	
	public Editora salvar(Editora editora) {
		return repository.save(editora);
	}
	
	public void excluir(Long id) {
		try {
			repository.deleteById(id);
		}
		catch(EmptyResultDataAccessException e) {
			throw new EditoraNaoEncontradaException(id);
		}
		catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_EDITORA_EM_USO, id));
		}
	}
	
	public Editora buscarOuFalhar(Long id) {
		return repository.findById(id).orElseThrow(() ->
		new EditoraNaoEncontradaException(id));
	}

	public List<Editora> findByName(String nome, Integer page, String order) {
		Pageable pageable = pageableBuilder(page, order);
		
		Page<Editora> list = repository.findByNomeContaining(nome, pageable);
		return list.toList();
	}

	public List<Editora> listarPorPagina(Integer page, String order) {
		Pageable pageable = pageableBuilder(page, order);
		
		return repository.findAll(pageable).toList();
	}

	@SuppressWarnings("unused")
	private Pageable pageableBuilder(Integer page, String order) {
		Sort sort = null;
		Pageable pageable = null;
		
		if(order.equals("asc")) {
			sort = Sort.by("nome").ascending();
			return pageable = PageRequest.of(page - 1, 20, sort);
		}
		else if(order.equals("desc")) {
			sort = Sort.by("nome").descending();
			return pageable = PageRequest.of(page - 1, 20, sort);
		}
		else {
			return pageable = PageRequest.of(page - 1, 20);
		}
	}
	
}
