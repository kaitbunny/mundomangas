package net.mundomangas.backend.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
	
	@Column(nullable = true)
	private Boolean colorido;
	
	@ManyToOne
	@JoinColumn(name = "cozinha_id", nullable = false)
	private Editora editora;
	
}
