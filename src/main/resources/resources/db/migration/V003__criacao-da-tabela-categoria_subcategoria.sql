CREATE TABLE categoria_subcategoria(
	categoria_id BIGINT NOT NULL,
    subcategoria_id BIGINT NOT NULL,
    
    CONSTRAINT fk_categoria_subcategoria_categoria FOREIGN KEY(categoria_id) REFERENCES categoria(id),
    CONSTRAINT fk_categoria_subcategoria_subcategoria FOREIGN KEY(subcategoria_id) REFERENCES categoria(id),
    
    PRIMARY KEY(categoria_id, subcategoria_id)
)engine=InnoDB default charset=utf8mb4;