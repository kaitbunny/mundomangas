CREATE TABLE estado(
	id BIGINT AUTO_INCREMENT NOT NULL,
    nome VARCHAR(100) NOT NULL,
    sigla VARCHAR(2) NOT NULL,
    
    PRIMARY KEY(id)
)engine=InnoDB default charset=utf8mb4;

CREATE TABLE cidade(
	id BIGINT AUTO_INCREMENT NOT NULL,
    nome VARCHAR(120) NOT NULL,
    estado_id BIGINT NOT NULL,
    
    FOREIGN KEY(estado_id) REFERENCES estado(id),
    PRIMARY KEY(id)
)engine=InnoDB default charset=utf8mb4;

CREATE TABLE bairro(
	id BIGINT AUTO_INCREMENT NOT NULL,
    nome VARCHAR(120) NOT NULL,
    cidade_id BIGINT NOT NULL,
    
    FOREIGN KEY(cidade_id) REFERENCES cidade(id),
    PRIMARY KEY(id)
)engine=InnoDB default charset=utf8mb4;

CREATE TABLE logradouro(
	id BIGINT AUTO_INCREMENT NOT NULL,
    nome VARCHAR(120) NOT NULL,
    cep VARCHAR(8) NOT NULL,
    bairro_id BIGINT NOT NULL,
    
    FOREIGN KEY(bairro_id) REFERENCES bairro(id),
    PRIMARY KEY(id)
)engine=InnoDB default charset=utf8mb4;

CREATE TABLE usuario_endereco(
	id BIGINT AUTO_INCREMENT NOT NULL,
    usuario_id BIGINT NOT NULL,
    endereco_id BIGINT NOT NULL,
    numero_endereco VARCHAR(30) NOT NULL,
    
    UNIQUE(usuario_id, endereco_id, numero_endereco),
    FOREIGN KEY(usuario_id) REFERENCES usuario(id),
    FOREIGN KEY(endereco_id) REFERENCES logradouro(id),
    
    PRIMARY KEY(id)
)engine=InnoDB default charset=utf8mb4;