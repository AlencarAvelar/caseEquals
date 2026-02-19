# Desafio Técnico Equals - Processador de Transações

Este projeto é uma solução robusta para processamento de arquivos de transações financeiras (layout posicional), persistência em banco de dados relacional e visualização via relatório web.

A aplicação foi desenvolvida com foco em **Extensibilidade**, **Clean Code** e **Facilidade de Execução** (Docker).

---

## 🚀 Tecnologias Utilizadas

* **Linguagem:** Java 21
* **Framework:** Spring Boot 3
* **Banco de Dados:** PostgreSQL 15
* **Infraestrutura:** Docker & Docker Compose
* **Build Tool:** Maven
* **Frontend:** HTML5, CSS3, Bootstrap 5 e Javascript (Vanilla)

---

## 🏛️ Arquitetura e Design Patterns

O projeto foi estruturado seguindo a arquitetura em camadas (MVC/Layered Architecture), garantindo separação de responsabilidades. Abaixo, os principais padrões utilizados:

### 1. Strategy Pattern (Padrão de Projeto Comportamental)
Utilizado para o processamento das linhas do arquivo.
* **Problema:** O sistema precisa aceitar diferentes bandeiras (Visa, Mastercard), e novas podem surgir (Elo, Amex). Usar `if/else` violaria o princípio *Open/Closed* do SOLID.
* **Solução:** Criamos a interface `PaymentStrategy`. Cada bandeira tem sua própria classe (`VisaStrategy`, `MastercardStrategy`) que encapsula a lógica de leitura posicional específica. O Spring injeta automaticamente todas as estratégias, e o serviço seleciona a correta em tempo de execução.

### 2. DTO (Data Transfer Object)
Utilizado para desacoplar a camada de persistência da camada de apresentação.
* **Objetivo:** A entidade `Transaction` reflete o banco de dados. O `TransactionDTO` reflete o que a tela precisa ver, aplicando formatações de data (`dd/MM/yyyy`) e moeda (`R$ 0,00`).

### 3. Global Exception Handling
Utilização de `@ControllerAdvice` e `@ExceptionHandler`.
* **Objetivo:** Centralizar o tratamento de erros, garantindo que o cliente (API/Frontend) receba mensagens padronizadas (ex: HTTP 400 em caso de arquivo inválido).

---

## 🌟 Funcionalidades

* **Mapeamento Completo (Homologação):** Diferente de uma extração básica, o parser foi configurado para ler e persistir todos os campos do Registro Tipo 1 (Detalhe), incluindo taxas, tarifas, dados de parcelamento e segurança (BIN/CV), permitindo uma auditoria completa do arquivo.
* **Filtros Dinâmicos:** O relatório permite filtrar as transações pela **Previsão de Pagamento** e por **Bandeira (Visa/Mastercard)**, podendo ser usados em conjunto ou isoladamente. Isso permite análises mais ricas, já que a data de evento costuma ser a mesma para o lote todo, enquanto a previsão de pagamento varia conforme as parcelas.
* **Tabela Responsiva:** Interface adaptada com barra de rolagem horizontal nativa para visualização confortável de todos os dados extraídos.

---

## ⚡ Otimizações de Performance

Para garantir que a aplicação se mantenha rápida e responsiva mesmo ao processar e exibir um grande volume de dados, foram aplicadas as seguintes otimizações técnicas:

* **Backend (Indexação no Banco de Dados):** Foram criados Índices (`@Index`) nas colunas `data_prevista_pagamento` e `bandeira` no PostgreSQL. Isso elimina a necessidade de *Table Scans* (leitura linha a linha) durante os filtros, garantindo buscas quase instantâneas.
* **Frontend (Manipulação Eficiente do DOM):** Ao invés de forçar o navegador a redesenhar a tabela a cada nova linha inserida (o que causaria congelamento da tela), o Javascript processa todas as transações em memória e realiza **uma única atualização do DOM** ao final. Isso permite carregar centenas de registros com dezenas de colunas de forma totalmente fluida.

---

## 🛠️ Como Executar o Projeto

A aplicação é "Dockerizada", o que significa que você **não precisa** ter Java ou PostgreSQL instalados na sua máquina local. Apenas o Docker é necessário.

### Pré-requisitos
* [Docker](https://www.docker.com/) e Docker Compose instalados.

### Passo a Passo

1.  **Clone o repositório** (ou extraia os arquivos):
    ```bash
    git clone [https://github.com/AlencarAvelar/caseEquals.git](https://github.com/AlencarAvelar/caseEquals.git)
    cd CaseEquals
    ```

2.  **Suba a aplicação com Docker Compose:**
    ```bash
    docker-compose up --build
    ```
    *Aguarde alguns instantes até aparecer a mensagem no terminal: `Started CaseEqualsApplication`.*

3.  **Acesse a Aplicação:**
    Abra o navegador e vá para: **[http://localhost:8080](http://localhost:8080)**

---

## 🧪 Como Testar

1.  **Upload:**
    * Na tela inicial, realize o upload do arquivo de exemplo `processoSeletivoEquals.txt`
2.  **Relatório e Filtros:**
    * Após o upload, a tabela será carregada com todas as colunas detalhadas.
    * Utilize os campos **"De", "Até"** e **"Bandeira"** para buscar transações específicas e clique em "Atualizar".

---

## 🔌 Documentação da API (Endpoints)

A API RESTful responde nos seguintes endpoints:

### 1. Upload de Arquivo
* **Método:** `POST` | **URL:** `/api/transactions/upload`
* **Content-Type:** `multipart/form-data`
* **Parâmetro (Body):** `file` (Arquivo .txt obrigatório)
* **Resposta (200 OK):** Retorna a quantidade de transações efetivamente salvas.

### 2. Listar Transações (Com Filtros)
* **Método:** `GET` | **URL:** `/api/transactions`
* **Query Params (Opcionais):**
    * `inicio`: Data inicial para a **Previsão de Pagamento** (`yyyy-MM-dd`).
    * `fim`: Data final para a **Previsão de Pagamento** (`yyyy-MM-dd`).
    * `bandeira`: Nome da bandeira (`VISA` ou `MASTERCARD`).
* **Exemplo de Chamada:**
  `GET /api/transactions?inicio=2018-10-25&bandeira=VISA`

---

## 📝 Banco de Dados

O banco de dados PostgreSQL é criado automaticamente pelo Docker.
* **Tabela:** `transacoes`
* **Script de Referência:** Veja `src/main/resources/schema.sql` para consultar o DDL utilizado.

---
**Desenvolvido por Alencar Avelar**