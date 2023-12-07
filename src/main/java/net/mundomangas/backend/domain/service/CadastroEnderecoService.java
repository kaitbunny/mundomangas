package net.mundomangas.backend.domain.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import net.mundomangas.backend.domain.dto.ConsumirEnderecoDTO;
import net.mundomangas.backend.domain.dto.EnderecoDTO;
import net.mundomangas.backend.domain.dto.UsuarioEnderecoDTO;
import net.mundomangas.backend.domain.exception.AtributoDeEnderecoNaoEncontradoException;
import net.mundomangas.backend.domain.exception.CepInvalidoException;
import net.mundomangas.backend.domain.exception.EntidadeEmUsoException;
import net.mundomangas.backend.domain.model.Endereco;
import net.mundomangas.backend.domain.model.Usuario;
import net.mundomangas.backend.domain.repository.EnderecoRepository;

@Service
public class CadastroEnderecoService {
	@Autowired
	private EnderecoRepository repository;

	private static final int ITENS_POR_PAGINA = 20;

	public void salvar(Usuario usuario, UsuarioEnderecoDTO endereco) {
		try {
			Endereco enderecoNovo;
			try {
				enderecoNovo = consumirAPI(endereco.cep(), endereco.numero(), usuario);

				repository.save(enderecoNovo);
			} catch (DataIntegrityViolationException e) {
				throw new EntidadeEmUsoException(
						String.format("Endereco de cep: '%s' e número '%s' já estão cadastrado na sua conta!",
								endereco.cep(), endereco.numero()));
			} catch (Exception e) {
				throw new CepInvalidoException(endereco.cep());
			}
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException("Endereco já cadastrado!");
		}
	}

	public void excluir(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new AtributoDeEnderecoNaoEncontradoException(id);
		}
	}

	public PaginatedResponseService<EnderecoDTO> listarPorPagina(Integer page, Usuario usuario) {
		Pageable pageable = pageableBuilder(page);

		Page<Endereco> endereco = repository.findByUsuario_Id(usuario.getId(), pageable);

		Page<EnderecoDTO> result = endereco.map(e -> new EnderecoDTO(e));

		return responseBuilder(result, page);
	}

	public Endereco buscarOuFalhar(Long id) {
		return repository.findById(id).orElseThrow(() -> new AtributoDeEnderecoNaoEncontradoException(id));
	}

	private PaginatedResponseService<EnderecoDTO> responseBuilder(Page<EnderecoDTO> result, Integer page) {
		return new PaginatedResponseService<>(result.getContent(), result.getTotalPages(), result.getTotalElements(),
				page);
	}

	private Pageable pageableBuilder(Integer page) {
		int pageNumber = page - 1;

		return PageRequest.of(pageNumber, ITENS_POR_PAGINA);
	}

	public Endereco consumirAPI(String cep, String numero, Usuario usuario) throws Exception {
		URL url = new URL("https://viacep.com.br/ws/" + cep + "/json/");
		URLConnection connection = url.openConnection();

		InputStream is = connection.getInputStream();

		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

		String linha = "";
		StringBuilder jsonCep = new StringBuilder();

		while ((linha = br.readLine()) != null) {
			jsonCep.append(linha);
		}
		
		ConsumirEnderecoDTO result = new Gson().fromJson(jsonCep.toString(), ConsumirEnderecoDTO.class);
		
		Endereco template = new Endereco();
		template.setUf(result.uf());
		template.setLocalidade(result.localidade());
		template.setBairro(result.bairro());
		template.setLogradouro(result.logradouro());
		template.setCep(cep);
		template.setNumero(numero);
		template.setUsuario(usuario);
		
		return template;
	}
}
