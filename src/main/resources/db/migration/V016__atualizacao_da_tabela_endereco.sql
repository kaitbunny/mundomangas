DROP TABLE usuario_endereco;
DROP TABLE logradouro;
DROP TABLE bairro;
DROP TABLE cidade;
DROP TABLE estado;

CREATE TABLE endereco (
	id BIGINT AUTO_INCREMENT NOT NULL,
    uf VARCHAR(2) NOT NULL,
    localidade VARCHAR(120) NOT NULL,
    bairro VARCHAR(120) NOT NULL,
    logradouro VARCHAR(120) NOT NULL,
    cep VARCHAR(8) NOT NULL,
    numero VARCHAR(30) NOT NULL,
    usuario_id BIGINT NOT NULL,
    
    UNIQUE(cep, numero, usuario_id),
    FOREIGN KEY(usuario_id) REFERENCES usuario(id),
    PRIMARY KEY(id)
)engine=InnoDB default charset=utf8mb4;