-- Criação da tabela de Transações
-- Este script reflete a estrutura gerada pelo JPA baseada na Entidade Transaction.java

CREATE TABLE IF NOT EXISTS transacoes (
    -- Identificador Primário
    id BIGSERIAL PRIMARY KEY,

    -- Identificação da Transação
    estabelecimento VARCHAR(255),
    nsu VARCHAR(255),

    -- Datas e Horas
    data_transacao DATE,
    data_evento DATE,
    hora_evento TIME,
    data_prevista_pagamento DATE,

    -- Valores Monetários (Numeric para precisão financeira)
    valor_total NUMERIC(19, 2),
    valor_parcela_ou_liquido NUMERIC(19, 2),
    valor_original NUMERIC(19, 2),
    valor_liquido_transacao NUMERIC(19, 2),

    -- Taxas e Tarifas
    taxa_parcelamento_comprador NUMERIC(19, 2),
    tarifa_boleto_comprador NUMERIC(19, 2),
    taxa_parcelamento_vendedor NUMERIC(19, 2),
    taxa_intermediacao NUMERIC(19, 2),
    tarifa_intermediacao NUMERIC(19, 2),
    tarifa_boleto_vendedor NUMERIC(19, 2),
    repasse_aplicacao NUMERIC(19, 2),

    -- Detalhes da Venda
    tipo_evento VARCHAR(255),
    tipo_transacao VARCHAR(255),
    numero_serie_leitor VARCHAR(255),
    codigo_transacao VARCHAR(255),
    codigo_pedido VARCHAR(255),

    -- Dados de Parcelamento
    indicador_pagamento VARCHAR(255),
    plano VARCHAR(255),
    parcela VARCHAR(255),
    qtd_parcelas INTEGER,

    -- Status e Dados de Captura
    status_pagamento VARCHAR(255),
    meio_pagamento VARCHAR(255),
    bandeira VARCHAR(255),
    canal_entrada VARCHAR(255),
    leitor VARCHAR(255),
    meio_captura VARCHAR(255),
    numero_logico VARCHAR(255),

    -- Dados do Cartão (Segurança)
    cartao_bin VARCHAR(255),
    cartao_holder VARCHAR(255),
    codigo_autorizacao VARCHAR(255),
    codigo_cv VARCHAR(255)
    );