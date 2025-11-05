# Usa a imagem base do OpenJDK 17 com Alpine Linux para um tamanho menor
FROM eclipse-temurin:17-jdk-jammy

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia o arquivo pom.xml para que o Maven possa baixar as dependências
# Isso aproveita o cache do Docker se as dependências não mudarem
COPY pom.xml .

# Copia o código fonte
COPY src ./src

# Compila o projeto e cria o JAR executável
# O -DskipTests é usado para pular os testes durante a construção
RUN apt-get update && apt-get install -y maven && \
    mvn clean package -DskipTests

# Define o ponto de entrada para executar o JAR
# O nome do JAR é geralmente <artifactId>-<version>.jar
# Neste caso, produtor-rural-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "target/produtor-rural-0.0.1-SNAPSHOT.jar"]

# Expõe a porta padrão do Spring Boot
EXPOSE 8080
