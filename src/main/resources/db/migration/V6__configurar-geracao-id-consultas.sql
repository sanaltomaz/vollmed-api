CREATE SEQUENCE consultas_id_seq;

ALTER TABLE consultas
ALTER COLUMN id
SET DEFAULT nextval('consultas_id_seq');

SELECT setval(
    'consultas_id_seq',
    COALESCE((SELECT MAX(id) FROM consultas), 1),
    (SELECT COUNT(*) > 0 FROM consultas)
);

ALTER SEQUENCE consultas_id_seq
OWNED BY consultas.id;