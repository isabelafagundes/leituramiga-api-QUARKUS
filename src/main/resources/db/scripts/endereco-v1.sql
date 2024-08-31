-- liquibase formatted sql
-- changeset auth:endereco-v1 splitStatements:true endDelimiter:;

CREATE TABLE IF NOT EXISTS cidade (
    codigo_cidade BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(60) NOT NULL,
    estado VARCHAR(2) NOT NULL
    );

CREATE TABLE IF NOT EXISTS endereco (
    codigo_endereco BIGINT AUTO_INCREMENT PRIMARY KEY,
    cep VARCHAR(8) NOT NULL,
    logradouro VARCHAR(120) NOT NULL,
    numero VARCHAR(10) NOT NULL,
    complemento VARCHAR(120),
    bairro VARCHAR(120) NOT NULL,
    endereco_principal BIT NOT NULL DEFAULT 0,
    codigo_cidade BIGINT NOT NULL,
    email_usuario VARCHAR(260) NOT NULL,
    FOREIGN KEY (email_usuario) REFERENCES usuario(email),
    FOREIGN KEY (codigo_cidade) REFERENCES cidade(codigo_cidade)
);