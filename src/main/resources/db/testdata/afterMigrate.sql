SET foreign_key_checks = 0;

DELETE FROM categoria_categoria_pai;
DELETE FROM categoria;
DELETE FROM editora;

SET foreign_key_checks = 1;

ALTER TABLE categoria AUTO_INCREMENT = 1;
ALTER TABLE editora AUTO_INCREMENT = 1;

INSERT INTO categoria(nome) VALUES('Marvel'), ('DC'), ('Fantasia'), ('HQ'), ('Manga'), ('Hulk'), ('Batman'), ('Isekai'), ('Konosuba');

INSERT INTO editora(nome, uri_foto) VALUES('Marvel Comics', 'https://static.displate.com/1200x857/displate/2023-02-20/26fad5ee3cd356d75cbc659d4b0b85ee_43aedd7741e9a846d17402a5d8c5a5cb.jpg');
INSERT INTO editora(nome, uri_foto) VALUES('DC Comics', 'https://i.pinimg.com/564x/3d/33/89/3d338995debeff7a573737e8a5b3e826.jpg');
INSERT INTO editora(nome, uri_foto) VALUES('Panini Comics', 'https://upload.wikimedia.org/wikipedia/commons/1/1b/Panini_comics_logo.png');

INSERT INTO categoria_categoria_pai(categoria_id, categoria_pai_id) VALUES(6, 1), (6, 4), (7, 2), (7, 4), (8, 3), (8, 5), (9, 8);