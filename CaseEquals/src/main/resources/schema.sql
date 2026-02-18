
-- explica como e feita a criacao das tabelas no postgre utilizando o JPA
CREATE TABLE IF NOT EXISTS transacoes (
    id SERIAL PRIMARY KEY,
    estabelecimento VARCHAR(10) NOT NULL,
    data_transacao DATE NOT NULL,
    data_evento DATE NOT NULL,
    hora_evento TIME NOT NULL,
    valor_total NUMERIC(13, 2) NOT NULL,
    bandeira VARCHAR(30) NOT NULL,
    nsu VARCHAR(11)
    );