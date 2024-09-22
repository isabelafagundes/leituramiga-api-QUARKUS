-- liquibase formatted sql
-- changeset auth:leituramiga-v1 splitStatements:true endDelimiter:;

CREATE TABLE IF NOT EXISTS categoria
(
    codigo_categoria BIGINT PRIMARY KEY AUTO_INCREMENT,
    descricao        VARCHAR(120)
);

CREATE TABLE IF NOT EXISTS cidade
(
    codigo_cidade BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome          VARCHAR(60) NOT NULL,
    estado        VARCHAR(2)  NOT NULL
);

CREATE TABLE IF NOT EXISTS instituicao
(
    codigo_instituicao BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome               VARCHAR(60),
    sigla              VARCHAR(10)
);

CREATE TABLE IF NOT EXISTS usuario
(
    email_usuario      VARCHAR(260) PRIMARY KEY,
    nome               VARCHAR(40) NOT NULL,
    username           VARCHAR(30) NOT NULL,
    tipo_usuario       INTEGER              DEFAULT 1,
    celular            VARCHAR(11),
    senha              VARCHAR(60),
    descricao          VARCHAR(120),
    imagem             VARCHAR(256),
    ativo              BIT         NOT NULL DEFAULT 1,
    tentativas         INTEGER     NOT NULL DEFAULT 3,
    bloqueado          BIT         NOT NULL DEFAULT 0,
    codigo_alteracao   VARCHAR(8),
    codigo_instituicao BIGINT,
    FOREIGN KEY (codigo_instituicao) REFERENCES instituicao (codigo_instituicao)
);

CREATE TABLE IF NOT EXISTS endereco
(
    codigo_endereco    BIGINT PRIMARY KEY AUTO_INCREMENT,
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

CREATE TABLE IF NOT EXISTS solicitacao
(
    codigo_solicitacao        BIGINT PRIMARY KEY AUTO_INCREMENT,
    data_criacao              VARCHAR(10) NOT NULL,
    hora_criacao              VARCHAR(8)  NOT NULL,
    data_atualizacao          VARCHAR(10) NOT NULL,
    hora_atualizacao          VARCHAR(8)  NOT NULL,
    data_entrega              VARCHAR(10) NOT NULL,
    hora_entrega              VARCHAR(8)  NOT NULL,
    data_devolucao            VARCHAR(10),
    hora_devolucao            VARCHAR(8),
    data_aceite               VARCHAR(10),
    hora_aceite               VARCHAR(8),
    motivo_recusa             VARCHAR(120),
    informacoes_adicionais    VARCHAR(256),
    codigo_tipo_solicitacao   INTEGER     NOT NULL,
    codigo_status_solicitacao INTEGER     NOT NULL DEFAULT 2,
    email_usuario_solicitante VARCHAR(260),
    email_usuario_receptor    VARCHAR(260),
    codigo_forma_entrega      INTEGER     NOT NULL,
    codigo_endereco           BIGINT      NOT NULL,
    codigo_rastreio_correio   VARCHAR(120),
    FOREIGN KEY (email_usuario_solicitante) REFERENCES usuario (email_usuario),
    FOREIGN KEY (email_usuario_receptor) REFERENCES usuario (email_usuario),
    FOREIGN KEY (codigo_endereco) REFERENCES endereco (codigo_endereco)
);

CREATE TABLE IF NOT EXISTS livro
(
    codigo_livro              BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome                      VARCHAR(120) NOT NULL,
    descricao                 VARCHAR(120) NOT NULL,
    estado_fisico             VARCHAR(160) NOT NULL,
    ultima_solicitacao        VARCHAR(25),
    email_usuario             VARCHAR(260) NOT NULL,
    codigo_ultima_solicitacao BIGINT       NOT NULL,
    codigo_categoria          BIGINT       NOT NULL,
    codigo_status_livro       BIGINT       NOT NULL,
    codigo_cidade             BIGINT       NOT NULL,
    FOREIGN KEY (codigo_cidade) REFERENCES cidade (codigo_cidade),
    FOREIGN KEY (email_usuario) REFERENCES usuario (email_usuario),
    FOREIGN KEY (codigo_ultima_solicitacao) REFERENCES solicitacao (codigo_solicitacao),
    FOREIGN KEY (codigo_categoria) REFERENCES categoria (codigo_categoria)
);

CREATE TABLE IF NOT EXISTS imagem_livro
(
    codigo_imagem_livro BIGINT PRIMARY KEY AUTO_INCREMENT,
    imagem              VARCHAR(256),
    codigo_livro        BIGINT NOT NULL,
    FOREIGN KEY (codigo_livro) REFERENCES livro (codigo_livro)
);

CREATE TABLE IF NOT EXISTS notificacao
(
    codigo_notificacao BIGINT PRIMARY KEY AUTO_INCREMENT,
    hora_criacao       VARCHAR(8)   NOT NULL,
    data_criacao       VARCHAR(10)  NOT NULL,
    descricao          VARCHAR(120) NOT NULL,
    codigo_solicitacao BIGINT       NOT NULL,
    email_usuario      VARCHAR(260) NOT NULL,
    FOREIGN KEY (codigo_solicitacao) REFERENCES solicitacao (codigo_solicitacao),
    FOREIGN KEY (email_usuario) REFERENCES usuario (email_usuario)
);

CREATE TABLE IF NOT EXISTS livro_solicitacao
(
    codigo_livro_solicitacao BIGINT PRIMARY KEY AUTO_INCREMENT,
    codigo_solicitacao       BIGINT       NOT NULL,
    codigo_livro             BIGINT       NOT NULL,
    email_usuario            VARCHAR(260) NOT NULL,
    FOREIGN KEY (email_usuario) REFERENCES usuario (email_usuario),
    FOREIGN KEY (codigo_solicitacao) REFERENCES solicitacao (codigo_solicitacao),
    FOREIGN KEY (codigo_livro) REFERENCES livro (codigo_livro)
);

CREATE TABLE IF NOT EXISTS comentario
(
    codigo_comentario     BIGINT PRIMARY KEY AUTO_INCREMENT,
    descricao             VARCHAR(120) NOT NULL,
    data_criacao          VARCHAR(40),
    hora_criacao          VARCHAR(40),
    email_usuario_criador VARCHAR(260) NOT NULL,
    email_usuario_perfil  VARCHAR(260) NOT NULL,
    FOREIGN KEY (email_usuario_criador) REFERENCES usuario (email_usuario)
);