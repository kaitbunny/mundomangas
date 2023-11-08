CREATE TABLE produto_categoria(
	produto_id BIGINT NOT NULL,
    categoria_id BIGINT NOT NULL,
    
	FOREIGN KEY(produto_id) REFERENCES produto(id),
    FOREIGN KEY(categoria_id) REFERENCES categoria(id),
    
    PRIMARY KEY(produto_id, categoria_id)
)engine=InnoDB default charset=utf8mb4;