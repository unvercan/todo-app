# multi-stage

# stage 1: build
FROM maven:3.8.8-eclipse-temurin-17 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# stage 2: run
FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY --from=builder /app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
