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
* **Frontend:** HTML5, CSS3 e Javascript (Vanilla)

---

## 🏛️ Arquitetura e Design Patterns

O projeto foi estruturado seguindo a arquitetura em camadas (MVC/Layered Architecture), garantindo separação de responsabilidades. Abaixo, os principais padrões utilizados:

### 1. Strategy Pattern (Padrão de Projeto Comportamental)
Utilizado para o processamento das linhas do arquivo.
* **Problema:** O sistema precisa aceitar diferentes bandeiras (Visa, Mastercard), e novas podem surgir (Elo, Amex). Usar `if/else` violaria o princípio *Open/Closed* do SOLID.
* **Solução:** Criamos a interface `PaymentStrategy`. Cada bandeira tem sua própria classe (`VisaStrategy`, `MastercardStrategy`) que encapsula a lógica de leitura posicional específica. O Spring injeta automaticamente todas as estratégias, e o serviço seleciona a correta em tempo de execução.

### 2. DTO (Data Transfer Object)
Utilizado para desacoplar a camada de persistência da camada de apresentação.
* **Objetivo:** A entidade `Transaction` reflete o banco de dados. O `TransactionDTO` reflete o que a tela precisa ver.
* **Benefício:** Permite formatar dados (ex: converter `BigDecimal` para String `R$ 100,00` ou datas para `dd/MM/yyyy`) sem poluir a entidade de domínio e sem expor a estrutura interna do banco.

### 3. Global Exception Handling
Utilização de `@ControllerAdvice` e `@ExceptionHandler`.
* **Objetivo:** Centralizar o tratamento de erros.
* **Benefício:** Evita blocos `try-catch` repetitivos nos Controllers e garante que o cliente (API/Frontend) receba mensagens de erro padronizadas e amigáveis (ex: HTTP 400 com mensagem clara).

### 4. Utility Class
Criação da classe `ParserUtils`.
* **Objetivo:** Centralizar lógicas repetitivas de conversão (String para Data, String para Moeda).
* **Benefício:** Aplica o princípio DRY (Don't Repeat Yourself). Se o formato da data mudar no arquivo, alteramos em apenas um lugar.

---

## 🛠️ Como Executar o Projeto

A aplicação é "Dockerizada", o que significa que você **não precisa** ter Java ou PostgreSQL instalados na sua máquina local. Apenas o Docker é necessário.

### Pré-requisitos
* [Docker](https://www.docker.com/)  instalado.

### Passo a Passo

1.  **Clone o repositório** (ou extraia os arquivos):
    ```bash
    git clone https://github.com/AlencarAvelar/caseEquals.git
    cd CaseEquals
    ```

2.  **Suba a aplicação com Docker Compose:**
    Este comando irá compilar o projeto Java (dentro do container), baixar a imagem do Postgres e iniciar ambos.
    ```bash
    docker-compose up --build
    ```
    *Aguarde alguns instantes até aparecer a mensagem no terminal: `Started CaseEqualsApplication`.*

3.  **Acesse a Aplicação:**
    Abra o navegador e vá para:
    **[http://localhost:8080](http://localhost:8080)**

---

## 🧪 Como Testar

1.  **Upload:**
    * Na tela inicial, clique em "Escolher Arquivo".
    * Selecione o arquivo de exemplo `processoSeletivoEquals.txt` (disponível em `src/main/resources`).
    * Clique em "Processar Arquivo".

2.  **Relatório:**
    * Após o processamento, a tabela será carregada com as transações.
    * **Filtros:** Utilize os campos de data "De" e "Até" e clique em "Filtrar / Atualizar" para buscar transações por período (ex: 2018-09-25).

---
## 🔌 Documentação da API (Endpoints)

A aplicação segue o padrão RESTful. O Frontend se comunica com o Backend através das seguintes chamadas:

### 1. Upload de Arquivo
Endpoint responsável por receber o arquivo, identificar a bandeira (Strategy Pattern), tratar os dados e persistir no PostgreSQL.

* **Método:** `POST`
* **URL:** `/api/transactions/upload`
* **Content-Type:** `multipart/form-data`
* **Parâmetros de Corpo (Body):**
    * `file`: O arquivo de texto (.txt) a ser processado. 
* **Respostas:**
    * `200 OK`: "Arquivo processado com sucesso!"
    * `400 Bad Request`: "Erro ao processar arquivo: [Detalhe do erro]"

### 2. Listar Transações (Relatório)
Retorna a lista de transações do banco de dados, convertidas para DTO (Data Transfer Object) com os valores monetários e datas já formatados para exibição.

* **Método:** `GET`
* **URL:** `/api/transactions`
* **Parâmetros de Consulta (Query Params):**
    * `inicio`: Data de início para filtro (Formato: `yyyy-MM-dd`). **(Opcional)**
    * `fim`: Data de fim para filtro (Formato: `yyyy-MM-dd`). **(Opcional)**
* **Exemplo de Chamada:**
  `GET /api/transactions?inicio=2018-09-25&fim=2018-09-25`
* **Exemplo de Resposta (JSON):**
  ```json
  [
    {
      "loja": "LOJA A",
      "dataHora": "25/09/2018 às 14:00:00",
      "valor": "R$ 100,00",
      "bandeira": "VISA",
      "nsu": "123456"
    },
    {
      "loja": "LOJA B",
      "dataHora": "25/09/2018 às 15:30:00",
      "valor": "R$ 50,00",
      "bandeira": "MASTERCARD",
      "nsu": "789012"
    }
  ]

## 📂 Estrutura do Projeto

```text
src/main/java/com/equals/caseequals/
│
├── config/       # Configurações globais (ex: CORS)
├── controller/   # Endpoints da API (Upload e Listagem)
├── dto/          # Objetos de transferência de dados (Formatados para tela)
├── exception/    # Tratamento centralizado de erros
├── model/        # Entidades JPA (Banco de Dados)
├── repository/   # Interfaces de acesso a dados (Spring Data JPA)
├── service/      # Regras de negócio
│   ├── parser/   # Lógica do Strategy Pattern
│   │   ├── strategy/
│   │   └── FileProcessorService.java
├── utils/        # Formatadores de Data e Moeda
└── CaseEqualsApplication.java