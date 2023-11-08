DROP TABLE categoria_subcategoria;

CREATE TABLE categoria_categoria_pai(
	categoria_id BIGINT NOT NULL,
    categoria_pai_id BIGINT NOT NULL,
    
    CONSTRAINT fk_categoria_categoria FOREIGN KEY(categoria_id) REFERENCES categoria(id),
    CONSTRAINT fk_categoria_categoria_pai FOREIGN KEY(categoria_pai_id) REFERENCES categoria(id),
    
    PRIMARY KEY(categoria_id, categoria_pai_id)
)engine=InnoDB default charset=utf8mb4;