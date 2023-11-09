SET foreign_key_checks = 0;

DELETE FROM categoria;
DELETE FROM editora;
DELETE FROM produto_categoria;
DELETE FROM produto;
DELETE FROM usuario;
DELETE FROM carrinho;

SET foreign_key_checks = 1;

ALTER TABLE categoria AUTO_INCREMENT = 1;
ALTER TABLE editora AUTO_INCREMENT = 1;
ALTER TABLE produto AUTO_INCREMENT = 1;
ALTER TABLE usuario AUTO_INCREMENT = 1;
ALTER TABLE carrinho AUTO_INCREMENT = 1;

INSERT INTO categoria(nome) VALUES('Marvel'), ('DC'), ('Fantasia'), ('HQ'), ('Manga'), ('Hulk'), ('Batman'), ('Isekai'), ('Konosuba'), ('Ecchi'), ('Gigant');

INSERT INTO editora(nome, uri_foto) VALUES('Marvel Comics', 'https://static.displate.com/1200x857/displate/2023-02-20/26fad5ee3cd356d75cbc659d4b0b85ee_43aedd7741e9a846d17402a5d8c5a5cb.jpg');
INSERT INTO editora(nome, uri_foto) VALUES('DC Comics', 'https://i.pinimg.com/564x/3d/33/89/3d338995debeff7a573737e8a5b3e826.jpg');
INSERT INTO editora(nome, uri_foto) VALUES('Panini Comics', 'https://upload.wikimedia.org/wikipedia/commons/1/1b/Panini_comics_logo.png');
INSERT INTO editora(nome, uri_foto) VALUES('Abril', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQdy-naDU_dGfJWhg7XFe91hQ2vR6Z01wdedEJJu4fX9mx2LELU4JkDxPt-Cfy9gmQdg8E');

INSERT INTO produto(nome, paginas, uri_foto, data_publicacao, preco, estoque, total_vendido, ativo, colorido, editora_id) VALUES('Gigant # 01', 232, 'https://rika.vtexassets.com/arquivos/ids/397536-800-auto?v=637503311795030000&width=800&height=auto&aspect=true', '2019-12-01', 18.00, 12, 3, 1, 0, 3);
INSERT INTO produto(nome, paginas, uri_foto, data_publicacao, preco, estoque, total_vendido, ativo, colorido, editora_id) VALUES('Hulk # 070', 68, 'https://rika.vtexassets.com/arquivos/ids/239904-800-auto?v=635316700301670000&width=800&height=auto&aspect=true', '1989-04-01', 10.50, 7, 12, 1, 1, 4);
INSERT INTO produto(nome, paginas, uri_foto, data_publicacao, preco, estoque, total_vendido, ativo, colorido, editora_id) VALUES('Batman - 5ª Série # 44', 100, 'https://rika.vtexassets.com/arquivos/ids/236448-800-auto?v=635316655292030000&width=800&height=auto&aspect=true', '2000-06-01', 7.50, 23, 21, 1, 1, 4);

INSERT INTO produto_categoria(produto_id, categoria_id) VALUES(1, 5), (1, 3), (2, 1), (2, 4), (2, 6), (3, 2), (3, 4), (3, 7);

INSERT INTO usuario(nome, sobrenome, data_nascimento, cpf, email, senha, permissao) VALUES('admin', 'mundomangas', '2000-01-01', '00000000000', 'admin@mundomangas.com', '$2a$10$0jdH8i8TQOgCKCOKWpT.SeKSMyfT2jjGgKzU2NubchuuNn6dJx6T2', 'ADMIN');
INSERT INTO usuario(nome, sobrenome, data_nascimento, cpf, email, senha, permissao) VALUES('user', 'mundomangas', '2000-01-01', '11111111111', 'user@mundomangas.com', '$2a$10$uJOU5modsHDpJOZQZOym4ugyDzZ.3QhjiI2.rOdatcSVxZOBfbddy', 'USER');

INSERT INTO carrinho(produto_id, usuario_id, data_criacao, quantidade) VALUES(1, 2, CURDATE(), 10);