CREATE SCHEMA tenant1;
CREATE SCHEMA tenant2;
CREATE SCHEMA tenant3;
CREATE SCHEMA tenant4;
CREATE SCHEMA tenant5;

create TABLE IF NOT EXISTS cliente (
  id UUID NOT NULL,
  versao INTEGER NOT NULL,
  tenant VARCHAR(100) NULL,
  nome VARCHAR(100) NOT NULL,
  CONSTRAINT pk_cliente_id PRIMARY KEY (id)
);

create TABLE IF NOT EXISTS tenant1.cliente (
  id UUID NOT NULL,
  versao INTEGER NOT NULL,
  nome VARCHAR(100) NOT NULL,
  CONSTRAINT pk_cliente_id PRIMARY KEY (id)
);

create TABLE IF NOT EXISTS tenant2.cliente (
  id UUID NOT NULL,
  versao INTEGER NOT NULL,
  nome VARCHAR(100) NOT NULL,
  CONSTRAINT pk_cliente_id PRIMARY KEY (id)
);

create TABLE IF NOT EXISTS tenant3.cliente (
  id UUID NOT NULL,
  versao INTEGER NOT NULL,
  nome VARCHAR(100) NOT NULL,
  CONSTRAINT pk_cliente_id PRIMARY KEY (id)
);

create TABLE IF NOT EXISTS tenant4.cliente (
  id UUID NOT NULL,
  versao INTEGER NOT NULL,
  nome VARCHAR(100) NOT NULL,
  CONSTRAINT pk_cliente_id PRIMARY KEY (id)
);

create TABLE IF NOT EXISTS tenant5.cliente (
  id UUID NOT NULL,
  versao INTEGER NOT NULL,
  nome VARCHAR(100) NOT NULL,
  CONSTRAINT pk_cliente_id PRIMARY KEY (id)
);