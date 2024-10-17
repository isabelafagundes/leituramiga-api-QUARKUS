-- liquibase formatted sql
-- changeset auth:solicitacao-v2 splitStatements:true endDelimiter:;

ALTER TABLE solicitacao MODIFY COLUMN data_entrega VARCHAR(10);
ALTER TABLE solicitacao MODIFY COLUMN hora_entrega VARCHAR(8);