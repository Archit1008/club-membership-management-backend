# Use official OpenJDK image as base
FROM eclipse-temurin:21-jdk-alpine


# Set working directory inside container
WORKDIR /app

# Copy the JAR file (replace with your actual JAR name)
COPY target/eventmanagement-0.0.1-SNAPSHOT.jar app.jar
# Expose the port your Spring Boot app runs on
EXPOSE 8283

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
