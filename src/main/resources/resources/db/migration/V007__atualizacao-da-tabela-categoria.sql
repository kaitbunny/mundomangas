DROP TABLE produto_categoria;
DROP TABLE categoria_categoria_pai;
DROP TABLE categoria;

CREATE TABLE categoria(
	id BIGINT AUTO_INCREMENT NOT NULL,
    nome VARCHAR(60) NOT NULL,
    
    PRIMARY KEY(id)
)engine=InnoDB default charset=utf8mb4;

CREATE TABLE categoria_categoria_pai(
	categoria_id BIGINT NOT NULL,
    categoria_pai_id BIGINT NOT NULL,
    
    CONSTRAINT fk_categoria_categoria FOREIGN KEY(categoria_id) REFERENCES categoria(id),
    CONSTRAINT fk_categoria_categoria_pai FOREIGN KEY(categoria_pai_id) REFERENCES categoria(id),
    
    PRIMARY KEY(categoria_id, categoria_pai_id)
)engine=InnoDB default charset=utf8mb4;

CREATE TABLE produto_categoria(
	produto_id BIGINT NOT NULL,
    categoria_id BIGINT NOT NULL,
    
	FOREIGN KEY(produto_id) REFERENCES produto(id),
    FOREIGN KEY(categoria_id) REFERENCES categoria(id),
    
    PRIMARY KEY(produto_id, categoria_id)
)engine=InnoDB default charset=utf8mb4;