-- liquibase formatted sql
-- changeset auth:livro-v1 splitStatements:true endDelimiter:;

CREATE TABLE IF NOT EXISTS livro_solicitacao
(
    codigo    INTEGER PRIMARY KEY,
    descricao VARCHAR(120)
);

CREATE TABLE IF NOT EXISTS categoria
(
    codigo_categoria INTEGER PRIMARY KEY,
    descricao        VARCHAR(120)
);

CREATE TABLE IF NOT EXISTS livro
(
    codigo_livro              INTEGER PRIMARY KEY,
    nome                      VARCHAR(120) NOT NULL,
    descricao                 VARCHAR(120) NOT NULL,
    estado_fisico             VARCHAR(160) NOT NULL,
    ultima_solicitacao        VARCHAR(25),
    email_usuario             BIGINT       NOT NULL,
    codigo_ultima_solicitacao BIGINT       NOT NULL,
    codigo_categoria          BIGINT       NOT NULL,
    codigo_status_livro       BIGINT       NOT NULL,
    codigo_cidade            BIGINT       NOT NULL,
    FOREIGN KEY (codigo_cidade) REFERENCES cidade (codigo_cidade),
    FOREIGN KEY (email_usuario) REFERENCES usuario (email_usuario),
    FOREIGN KEY (codigo_ultima_solicitacao) REFERENCES livro_solicitacao (codigo),
    FOREIGN KEY (codigo_categoria) REFERENCES categoria (codigo_categoria),
);
