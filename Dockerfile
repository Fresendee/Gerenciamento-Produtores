
FROM eclipse-temurin:17-jdk-jammy


WORKDIR /app


COPY pom.xml .


COPY src ./src


RUN apt-get update && apt-get install -y maven && \
    mvn clean package -DskipTests


ENTRYPOINT ["java", "-jar", "target/produtor-rural-0.0.1-SNAPSHOT.jar"]


EXPOSE 8080
