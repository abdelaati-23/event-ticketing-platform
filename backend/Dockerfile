# Stage 1: Build the application using Maven
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app
# Copy pom and resolve dependencies first (caches this layer to speed up future builds)
COPY pom.xml .
RUN mvn dependency:go-offline
# Copy source code and build
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Create the minimal production image
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
# Copy only the compiled JAR from Stage 1
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]