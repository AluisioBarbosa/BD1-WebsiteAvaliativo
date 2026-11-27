CREATE TABLE usuario(
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL
);

CREATE TABLE professor(
    id BIGINT PRIMARY KEY,
    CONSTRAINT fk_professor_usuario
    FOREIGN KEY (id) REFERENCES usuario(id) ON DELETE CASCADE
);

CREATE TABLE aluno(
    id BIGINT PRIMARY KEY,
    CONSTRAINT fk_aluno_usuario
    FOREIGN KEY (id) REFERENCES usuario(id) ON DELETE CASCADE
);

CREATE TABLE avaliacao(
    id BIGSERIAL PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    descricao TEXT,
    hora_inicio TIMESTAMP NOT NULL,
    hora_fim TIMESTAMP NOT NULL,
    id_professor BIGINT NOT NULL,
    CONSTRAINT fk_avaliacao_professor
    FOREIGN KEY (id_professor) REFERENCES professor(id)
);

CREATE TABLE questao(
    id BIGSERIAL PRIMARY KEY,
    enunciado TEXT NOT NULL,
    tipo VARCHAR(50) NOT NULL CHECK (tipo IN ('MULTIPLA_ESCOLHA', 'DISSERTATIVA'))
);

CREATE TABLE alternativa(
    id BIGSERIAL PRIMARY KEY,
    descricao TEXT NOT NULL,
    correta BOOLEAN NOT NULL DEFAULT FALSE,
    id_questao BIGINT NOT NULL,
    CONSTRAINT fk_alternativa_questao
    FOREIGN KEY (id_questao) REFERENCES questao(id) ON DELETE CASCADE
);

CREATE TABLE avaliacao_questao(
    id_avaliacao BIGINT NOT NULL,
    id_questao BIGINT NOT NULL,
    valor DOUBLE PRECISION NOT NULL CHECK (valor > 0),
    ordem_questoes INTEGER NOT NULL,
    PRIMARY KEY (id_avaliacao, id_questao),
    CONSTRAINT fk_aq_avaliacao
    FOREIGN KEY (id_avaliacao) REFERENCES avaliacao(id) ON DELETE CASCADE,
    CONSTRAINT fk_aq_questao
    FOREIGN KEY (id_questao) REFERENCES questao(id) ON DELETE CASCADE
);

CREATE TABLE realiza(
    id_aluno BIGINT NOT NULL,
    id_avaliacao BIGINT NOT NULL,
    status_avaliacao VARCHAR(50) NOT NULL DEFAULT 'PENDENTE'
    CHECK (status_avaliacao IN ('PENDENTE', 'EM_ANDAMENTO', 'CONCLUIDA')),
    PRIMARY KEY (id_aluno, id_avaliacao),
    CONSTRAINT fk_realiza_aluno
    FOREIGN KEY (id_aluno) REFERENCES aluno(id) ON DELETE CASCADE,
    CONSTRAINT fk_realiza_avaliacao
    FOREIGN KEY (id_avaliacao) REFERENCES avaliacao(id) ON DELETE CASCADE
);

CREATE TABLE resposta(
    id BIGSERIAL PRIMARY KEY,
    id_aluno BIGINT NOT NULL,
    id_avaliacao BIGINT NOT NULL,
    id_questao BIGINT NOT NULL,
    id_alternativa BIGINT,
    texto_resposta TEXT,
    nota DOUBLE PRECISION,
    CONSTRAINT fk_resposta_aluno
    FOREIGN KEY (id_aluno) REFERENCES aluno(id) ON DELETE CASCADE,
    CONSTRAINT fk_resposta_avaliacao
    FOREIGN KEY (id_avaliacao) REFERENCES avaliacao(id) ON DELETE CASCADE,
    CONSTRAINT fk_resposta_questao
    FOREIGN KEY (id_questao) REFERENCES questao(id) ON DELETE CASCADE,
    CONSTRAINT fk_resposta_alternativa
    FOREIGN KEY (id_alternativa) REFERENCES alternativa(id) ON DELETE SET NULL
);
