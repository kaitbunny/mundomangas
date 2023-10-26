package net.mundomangas.backend.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Produto {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	@JsonIgnore
	@Column(nullable = false)
	private Integer paginas;
	
	@Column(nullable = true)
	private String uriFoto;
	
	@Column(nullable = true)
	private LocalDate dataPublicacao;
	
	@Column(nullable = false)
	private BigDecimal preco;
	
	@Column(nullable = true)
	private Integer estoque;
	
	@Column(nullable = true)
	private Integer totalVendido;
	
	@Column(nullable = true)
	private Boolean ativo;
	
	@JsonIgnore
	@Column(nullable = true)
	private Boolean colorido;
	
	@ManyToOne
	@JoinColumn(name = "editora_id", nullable = false)
	private Editora editora;
	
	@ManyToMany
	@JoinTable(name = "produto_categoria",
			joinColumns = @JoinColumn(name = "produto_id"),
			inverseJoinColumns = @JoinColumn(name = "categoria_id"))
	private List<Categoria> categorias = new ArrayList<>();
	
	@JsonProperty("descricao")
	public String getDescricao() {
	    String descricao = "Editora: " + this.editora.getNome() + ", " + this.paginas + " páginas";

	    if (this.dataPublicacao != null) {
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
	        descricao += ", data de publicação: " + this.dataPublicacao.format(formatter);
	    }

	    if (this.colorido != null) {
	        descricao += ", cor: " + (this.colorido ? "colorido" : "preto e branco");
	    }

	    return descricao;
	}
}
