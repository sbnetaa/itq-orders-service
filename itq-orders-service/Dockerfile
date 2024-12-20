FROM maven:3.8.6-openjdk-23-slim AS builder

WORKDIR /itq/orders-service

COPY pom.xml .
COPY src ./src

RUN mvn clean package

FROM openjdk:23-jdk-slim
WORKDIR /itq/orders-service

COPY --from=builder /itq/orders-service/target/*.jar itq-orders-service.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "itq-orders-service.jar"]