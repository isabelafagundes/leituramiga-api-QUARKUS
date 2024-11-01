-- liquibase formatted sql
-- changeset auth:solicitacao-endereco-v4 splitStatements:true endDelimiter:;

CREATE TABLE solicitacao_endereco
(
    codigo_endereco_solicitacao BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo_endereco             BIGINT       NOT NULL,
    codigo_solicitacao          BIGINT       NOT NULL,
    email_usuario               VARCHAR(260) NOT NULL,
    FOREIGN KEY (codigo_endereco) REFERENCES endereco (codigo_endereco),
    FOREIGN KEY (codigo_solicitacao) REFERENCES solicitacao (codigo_solicitacao),
    FOREIGN KEY (email_usuario) REFERENCES usuario (email_usuario)
);

ALTER TABLE solicitacao DROP COLUMN codigo_endereco;