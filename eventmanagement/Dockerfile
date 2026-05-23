# Stage 1: Build the JAR
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy pom.xml and download dependencies
COPY eventmanagement/pom.xml .
RUN mvn dependency:go-offline

# Copy source code
COPY eventmanagement/src ./src

# Build the JAR
RUN mvn clean package -DskipTests

# Stage 2: Run the JAR
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=build target/eventmanagement-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8283
ENTRYPOINT ["java", "-jar", "app.jar"]
