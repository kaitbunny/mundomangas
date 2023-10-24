
DROP TABLE produto_categoria;
DROP TABLE produto;

CREATE TABLE produto(
	id BIGINT AUTO_INCREMENT NOT NULL,
    nome VARCHAR(120) NOT NULL,
    paginas INT NOT NULL,
    uri_foto VARCHAR(255) DEFAULT 'https://i.im.ge/2023/10/10/P5v5wx.imagem-indisponivel.jpg',
    data_publicacao DATE,
    preco DECIMAL(6, 2) NOT NULL,
    estoque INT,
    total_vendido INT,
    ativo TINYINT(1),
    colorido TINYINT(1),
    editora_id BIGINT NOT NULL,
    
    CONSTRAINT fk_produto_editora FOREIGN KEY(editora_id) REFERENCES editora(id),
    PRIMARY KEY(id)
)engine=InnoDB default charset=utf8mb4;

CREATE TABLE produto_categoria(
	produto_id BIGINT NOT NULL,
    categoria_id BIGINT NOT NULL,
    
	FOREIGN KEY(produto_id) REFERENCES produto(id),
    FOREIGN KEY(categoria_id) REFERENCES categoria(id),
    
    PRIMARY KEY(produto_id, categoria_id)
)engine=InnoDB default charset=utf8mb4;
