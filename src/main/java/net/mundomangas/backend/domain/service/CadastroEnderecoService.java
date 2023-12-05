package net.mundomangas.backend.domain.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

import net.mundomangas.backend.domain.exception.CepInvalidoException;
import net.mundomangas.backend.domain.model.Bairro;
import net.mundomangas.backend.domain.model.Cidade;
import net.mundomangas.backend.domain.model.Estado;
import net.mundomangas.backend.domain.model.Logradouro;

@Service
public class CadastroEnderecoService {
	@Autowired
	private CadastroEstadoService estadoService;

	@Autowired
	private CadastroCidadeService cidadeService;

	@Autowired
	private CadastroBairroService bairroService;

	@Autowired
	private CadastroLogradouroService logradouroService;
	
	private String logradouro;
	private String bairro;
	private String localidade;
	private String uf;
	
	public void consumirAPI(String cep) throws Exception {
		URL url = new URL("https://viacep.com.br/ws/"+ cep +"/json/");
		URLConnection connection = url.openConnection();
		
		InputStream is = connection.getInputStream();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		
		String linha = "";
		StringBuilder jsonCep = new StringBuilder();
		
		while((linha = br.readLine()) != null) {
			jsonCep.append(linha);
		}
		
		EnderecoService data = new Gson().fromJson(jsonCep.toString(), EnderecoService.class);
		
		this.logradouro = data.logradouro();
		this.bairro = data.bairro();
		this.localidade = data.localidade();
		this.uf = data.uf();
	}
	
	@Transactional
	public Logradouro cadastrarPorCep(String cep) {
		try {
			consumirAPI(cep);
			
			Estado estadoTemplate = estadoService.buscarPorSigla(uf);
			
			Cidade cidadeData = new Cidade();
			if(cidadeService.buscarPorNome(localidade, estadoTemplate.getId()) == null) {
				cidadeData.setNome(localidade);
				cidadeData.setEstado(estadoTemplate);
				cidadeService.salvar(cidadeData);
			}
			cidadeData = cidadeService.buscarPorNome(localidade, estadoTemplate.getId());
			
			Bairro bairroData = new Bairro();
			if(bairroService.buscarPorNome(bairro, cidadeData.getId()) == null) {
				bairroData.setNome(bairro);
				bairroData.setCidade(cidadeData);
				bairroService.salvar(bairroData);
			}
			bairroData = bairroService.buscarPorNome(bairro, cidadeData.getId());
			
			Logradouro logradouroData = new Logradouro();
			if(logradouroService.buscarPorCep(cep) == null) {
				logradouroData.setNome(logradouro);
				logradouroData.setBairro(bairroData);
				logradouroData.setCep(cep);
				logradouroService.salvar(logradouroData);
			}
			logradouroData = logradouroService.buscarPorCep(cep);
			
			return logradouroData;
		} catch (Exception e) {
			throw new CepInvalidoException(cep);
		}
	}

}
