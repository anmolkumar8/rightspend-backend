# Use official Java 17 image
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy Maven files first (for caching)
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

# Install permissions (Linux)
RUN chmod +x mvnw

# Download dependencies
RUN ./mvnw dependency:go-offline

# Copy source code
COPY src src

# Build the application
RUN ./mvnw clean package -DskipTests

# Expose Spring Boot port
EXPOSE 8080

# Run the app
CMD ["java", "-jar", "target/rightspend-backend-0.0.1-SNAPSHOT.jar"]

