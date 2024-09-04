-- liquibase formatted sql
-- changeset auth:livro-v1 splitStatements:true endDelimiter:;

CREATE TABLE IF NOT EXISTS livro (
    codigo_livro INTEGER PRIMARY KEY,
    nome VARCHAR(120) NOT NULL,
    descricao VARCHAR(120),
    estado_fisico VARCHAR (160),
    ultima_solicitacao VARCHAR(25),
    email_usuario INTEGER,
    codigo_ultima_solicitacao INTEGER,
    codigo_categoria INTEGER,
    codigo_status_livro INTEGER
    /*FOREIGN KEY (email_usuario) REFERENCES usuario(email),
    FOREIGN KEY (codigo_ultima_solicitacao) REFERENCES livro_solicitacao(codigo),
    FOREIGN KEY (codigo_categoria) REFERENCES categoria(codigo_categoria),
    FOREIGN KEY (codigo_status_livro) REFERENCES status_livro(codigo_status_livro)*/
);
