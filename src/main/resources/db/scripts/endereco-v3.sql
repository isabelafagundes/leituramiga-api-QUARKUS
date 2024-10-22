-- liquibase formatted sql
-- changeset auth:endereco-v3 splitStatements:true endDelimiter:;

ALTER TABLE endereco ADD COLUMN ativo INTEGER NOT NULL DEFAULT 1;