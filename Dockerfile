# Etapa 1: Buildando apicação
FROM maven:3.9.9-eclipse-temurin-21-alpine as builder

WORKDIR /app

COPY pom.xml .

RUN mkdir -p src/main/java && mvn dependency:go-offline -B

COPY src ./src

RUN mvn clean package -DskipTests

# Etapa 2: Executando a aplicação
FROM eclipse-temurin:20-jre

WORKDIR /app

COPY --from=builder /app/target/SportifyApplication-0.1-PROD.jar SportifyApplication.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "SportifyApplication.jar"]