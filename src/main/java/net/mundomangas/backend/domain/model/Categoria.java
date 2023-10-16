package net.mundomangas.backend.domain.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Categoria {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	private String uri_foto;
	
	@ManyToMany
	@JoinTable(name = "categoria_subcategoria",
			joinColumns = @JoinColumn(name = "categoria_id"),
			inverseJoinColumns = @JoinColumn(name = "subcategoria_id"))
	private List<Categoria> categorias = new ArrayList<>();
}
