# caseEquals
Case Técnico Equals para Desenvolvimento de Software  - Homologação de meio de Pagamento

# Desafio Técnico Equals - Processamento de Arquivos CNAB

Aplicação desenvolvida para leitura de arquivos posicionais de vendas, persistência em banco de dados e visualização via relatório web.

## 🚀 Tecnologias Utilizadas

* **Java 21** & **Spring Boot 3**
* **PostgreSQL** (Banco de Dados)
* **Docker** & **Docker Compose** (Containerização)
* **HTML/JS** (Frontend leve para visualização)
* **Maven** (Gerenciador de dependências)

## 🏛️ Arquitetura e Padrões

O projeto segue a **Clean Architecture** (camadas) e utiliza o **Strategy Pattern** para o processamento de arquivos.
* **Objetivo:** Permitir a inclusão de novas bandeiras (ex: ELO, AMEX) apenas criando uma nova classe Strategy, sem alterar o serviço principal (Princípio Aberto/Fechado do SOLID).

## 🛠️ Como rodar a aplicação

Pré-requisito: Ter o **Docker** instalado.

1.  Clone este repositório ou extraia os arquivos.
2.  Na raiz do projeto, execute:
    ```bash
    docker-compose up --build
    ```
3.  Aguarde até aparecer a mensagem "Started CaseEqualsApplication".
4.  Acesse no navegador:
    **http://localhost:8080**

## 🧪 Como testar

1.  Na tela inicial, utilize o botão "Escolher Arquivo" para selecionar o arquivo `processoSeletivoEquals.txt`.
2.  Clique em "Processar".
3.  Utilize os filtros de data para visualizar as transações importadas.

## 📂 Estrutura do Banco de Dados

O banco PostgreSQL é iniciado automaticamente pelo Docker.
Script de criação (referência): `src/main/resources/schema.sql`.

---
Desenvolvido por [Alencar Avelar]

