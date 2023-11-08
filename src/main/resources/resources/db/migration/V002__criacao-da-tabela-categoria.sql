CREATE TABLE categoria(
	id BIGINT AUTO_INCREMENT NOT NULL,
    nome VARCHAR(60) NOT NULL,
    uri_foto VARCHAR(255) DEFAULT 'https://i.im.ge/2023/10/10/P5v5wx.imagem-indisponivel.jpg',
    
    PRIMARY KEY(id)
)engine=InnoDB default charset=utf8mb4;