CREATE TABLE consultas (
    id BIGINT PRIMARY KEY,
    id_medico BIGINT NOT NULL,
    id_paciente BIGINT NOT NULL,
    data TIMESTAMP NOT NULL,

    CONSTRAINT fk_consultas_medico
        FOREIGN KEY (id_medico)
        REFERENCES medicos(id),

    CONSTRAINT fk_consultas_paciente
        FOREIGN KEY (id_paciente)
        REFERENCES pacientes(id)
);