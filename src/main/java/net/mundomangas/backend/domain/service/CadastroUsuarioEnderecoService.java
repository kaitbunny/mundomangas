package net.mundomangas.backend.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import net.mundomangas.backend.domain.dto.EnderecoDTO;
import net.mundomangas.backend.domain.dto.UsuarioEnderecoDTO;
import net.mundomangas.backend.domain.exception.AtributoDeEnderecoNaoEncontradoException;
import net.mundomangas.backend.domain.model.Logradouro;
import net.mundomangas.backend.domain.model.Usuario;
import net.mundomangas.backend.domain.model.UsuarioEndereco;
import net.mundomangas.backend.domain.repository.UsuarioEnderecoRepository;

@Service
public class CadastroUsuarioEnderecoService {
	private static final String ENTIDADE = "endereco de usuario";
	private static final int ITENS_POR_PAGINA = 20;

	@Autowired
	private UsuarioEnderecoRepository repository;

	@Autowired
	UsuarioService usuarioService;

	@Autowired
	private CadastroEnderecoService enderecoService;

	public void salvar(Authentication authentication, UsuarioEnderecoDTO endereco) {
		Logradouro logradouro = enderecoService.cadastrarPorCep(endereco.cep());

		Usuario usuario = usuarioService.findUsuario(authentication);

		UsuarioEndereco usuarioEndereco = new UsuarioEndereco();
		usuarioEndereco.setEndereco(logradouro);
		usuarioEndereco.setUsuario(usuario);
		usuarioEndereco.setNumeroEndereco(endereco.numero());

		repository.save(usuarioEndereco);
	}

	public void excluir(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new AtributoDeEnderecoNaoEncontradoException(ENTIDADE, id);
		}
	}

	public PaginatedResponseService<EnderecoDTO> listarPorPagina(Integer page, Authentication authentication) {
		Usuario usuario = usuarioService.findUsuario(authentication);
		Pageable pageable = pageableBuilder(page);
		
		Page<UsuarioEndereco> usuarioEndereco = repository.findByUsuario_Id(usuario.getId(), pageable);
		
		Page<EnderecoDTO> result = usuarioEndereco.map(e -> new EnderecoDTO(e.getEndereco(), e.getNumeroEndereco()));
		
		return responseBuilder(result, page);
	}

	public UsuarioEndereco buscarOuFalhar(Long id) {
		return repository.findById(id).orElseThrow(() -> new AtributoDeEnderecoNaoEncontradoException(ENTIDADE, id));
	}

	private PaginatedResponseService<EnderecoDTO> responseBuilder(Page<EnderecoDTO> result, Integer page) {
		return new PaginatedResponseService<>(result.getContent(), result.getTotalPages(), result.getTotalElements(),
				page);
	}

	private Pageable pageableBuilder(Integer page) {
		int pageNumber = page - 1;

		return PageRequest.of(pageNumber, ITENS_POR_PAGINA);

	}
}
