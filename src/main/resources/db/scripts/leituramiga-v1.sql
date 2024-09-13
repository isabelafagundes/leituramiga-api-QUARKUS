-- liquibase formatted sql
-- changeset auth:leituramiga-v1 splitStatements:true endDelimiter:;

CREATE TABLE IF NOT EXISTS usuario
(
    email_usuario    VARCHAR(260) PRIMARY KEY,
    nome             VARCHAR(120) NOT NULL,
    username         VARCHAR(40)  NOT NULL,
    tipo_usuario     INT                   DEFAULT 1,
    senha            VARCHAR(260),
    ativo            BIT          NOT NULL DEFAULT 1,
    tentativas       INT          NOT NULL DEFAULT 3,
    bloqueado        BIT          NOT NULL DEFAULT 0,
    codigo_alteracao VARCHAR(260)
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
    codigo_cidade             BIGINT       NOT NULL,
    FOREIGN KEY (codigo_cidade) REFERENCES cidade (codigo_cidade),
    FOREIGN KEY (email_usuario) REFERENCES usuario (email_usuario),
    FOREIGN KEY (codigo_ultima_solicitacao) REFERENCES livro_solicitacao (codigo),
    FOREIGN KEY (codigo_categoria) REFERENCES categoria (codigo_categoria)
);

CREATE TABLE IF NOT EXISTS comentario
(
    codigo_comentario           INTEGER PRIMARY KEY AUTO_INCREMENT,
    descricao                   VARCHAR(120) NOT NULL,
    data_criacao                VARCHAR(40),
    hora_criacao                VARCHAR(40),
    email_usuario               VARCHAR(256) NOT NULL,

    FOREIGN KEY (email_usuario) REFERENCES usuario (email_usuario)
);

CREATE TABLE IF NOT EXISTS cidade
(
    codigo_cidade BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome          VARCHAR(60) NOT NULL,
    estado        VARCHAR(2)  NOT NULL
);

CREATE TABLE IF NOT EXISTS endereco
(
    codigo_endereco    BIGINT AUTO_INCREMENT PRIMARY KEY,
    cep                VARCHAR(8)   NOT NULL,
    logradouro         VARCHAR(120) NOT NULL,
    numero             VARCHAR(10)  NOT NULL,
    complemento        VARCHAR(120),
    bairro             VARCHAR(120) NOT NULL,
    endereco_principal BIT          NOT NULL DEFAULT 0,
    codigo_cidade      BIGINT       NOT NULL,
    email_usuario      VARCHAR(260) NOT NULL,
    FOREIGN KEY (email_usuario) REFERENCES usuario (email_usuario),
    FOREIGN KEY (codigo_cidade) REFERENCES cidade (codigo_cidade)
);