# Stage 1: Build
FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run
# FIX: Use eclipse-temurin because openjdk:21-jdk is removed from Docker Hub
FROM eclipse-temurin:21-jre-jammy AS runner
WORKDIR /app

# FIX: Removed the extra "./app" from the source path.
# Also using a wildcard (*) to handle version changes automatically.
COPY --from=builder /app/target/api-gateway-*.jar ./app.jar

EXPOSE 4004
ENTRYPOINT ["java", "-jar", "app.jar"]