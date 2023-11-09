DROP TABLE carrinho;
CREATE TABLE carrinho(
	id BIGINT AUTO_INCREMENT NOT NULL,
    produto_id BIGINT NOT NULL,
    usuario_id BIGINT NOT NULL,
    data_criacao DATE NOT NULL,
    quantidade INT NOT NULL,
    
    FOREIGN KEY(produto_id) REFERENCES produto(id),
    FOREIGN KEY(usuario_id) REFERENCES usuario(id),
    UNIQUE(produto_id, usuario_id),
    
    PRIMARY KEY(id)
)engine=InnoDB default charset=utf8mb4;