-- liquibase formatted sql
-- changeset auth:usuario-v1 splitStatements:true endDelimiter:;

CREATE TABLE IF NOT EXISTS usuario (
email VARCHAR(260) PRIMARY KEY,
nome VARCHAR(120) NOT NULL,
username VARCHAR(40) NOT NULL,
tipo_usuario INT DEFAULT 1,
senha VARCHAR(260),
ativo BIT NOT NULL DEFAULT 1,
tentativas INT NOT NULL DEFAULT 3,
bloqueado BIT NOT NULL DEFAULT 0,
codigo_alteracao VARCHAR(260)
);
