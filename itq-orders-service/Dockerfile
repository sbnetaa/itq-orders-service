FROM maven:3.9.9-eclipse-temurin-23 AS builder

WORKDIR /ready_app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -Dspring.datasource.url=jdbc:postgresql://db-orders:5432/itq-orders-db

FROM openjdk:23-jdk-slim
WORKDIR /ready_app

COPY --from=builder /ready_app/target/*.jar ready_app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "ready_app.jar"]