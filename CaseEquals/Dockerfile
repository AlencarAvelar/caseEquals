# --- Estágio 1: Build (Compilação) ---
# Usamos uma imagem com Maven para gerar o .jar
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia o arquivo de dependências primeiro (para aproveitar o cache do Docker)
COPY pom.xml .
# Baixa as dependências (isso faz o build ser mais rápido nas próximas vezes)
RUN mvn dependency:go-offline

# Copia todo o código fonte do projeto
COPY src ./src

# Compila o projeto e gera o arquivo .jar (pula os testes para ser mais rápido no build)
RUN mvn clean package -DskipTests

# --- Estágio 2: Run (Execução) ---
# Usamos uma imagem leve apenas com o JRE para rodar a aplicação
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copia o .jar gerado no estágio anterior para cá
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta 8080
EXPOSE 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]