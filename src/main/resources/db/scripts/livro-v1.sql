-- liquibase formatted sql
-- changeset auth:livro-v1 splitStatements:true endDelimiter:;

CREATE TABLE IF NOT EXISTS usuario
(
    email_usuario      INTEGER PRIMARY KEY,
    usarname           VARCHAR(30),
    nome               VARCHAR(40),
    senha              VARCHAR(60),
    celular            VARCHAR(11),
    descricao          VARCHAR(120),
    url_imagem         VARCHAR(256),
    codigo_instituicao BIGINT NOT NULL,
    codigo_endereco    BIGINT NOT NULL
);



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

CREATE TABLE IF NOT EXISTS status_livro
(
    codigo_status_livro INTEGER PRIMARY KEY,
    descricao           VARCHAR(80)
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

    FOREIGN KEY (email_usuario) REFERENCES usuario (email_usuario),
    FOREIGN KEY (codigo_ultima_solicitacao) REFERENCES livro_solicitacao (codigo),
    FOREIGN KEY (codigo_categoria) REFERENCES categoria (codigo_categoria),
    FOREIGN KEY (codigo_status_livro) REFERENCES status_livro (codigo_status_livro)

);
