DROP TABLE usuario;
CREATE TABLE usuario(
	id BIGINT AUTO_INCREMENT NOT NULL,
	nome VARCHAR(255) NOT NULL,
    sobrenome VARCHAR(255) NOT NULL,
    data_nascimento DATE NOT NULL,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    permissao VARCHAR(100) NOT NULL,
    
    PRIMARY KEY(id)
)engine=InnoDB default charset=utf8mb4;