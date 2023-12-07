SET foreign_key_checks = 0;

DELETE FROM estado;
DELETE FROM cidade;
DELETE FROM bairro;
DELETE FROM logradouro;
DELETE FROM usuario_endereco;

SET foreign_key_checks = 1;

DROP TABLE usuario_endereco;
DROP TABLE logradouro;
DROP TABLE bairro;
DROP TABLE cidade;
DROP TABLE estado;