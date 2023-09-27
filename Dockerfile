#Author: Badreddine TIRGANI

# Set the base image to OpenJDK 17
FROM openjdk:17-jdk-alpine

# Install Maven
RUN apk --no-cache add maven

# Set the working directory to /app
WORKDIR /app

# Copy the project files to the container
COPY . .

# Download project dependencies
RUN mvn dependency:go-offline

# Build the project
RUN mvn package -DskipTests

# Expose port 8080 for the backend application
EXPOSE 8080

# Start the backend Spring Boot application
CMD ["java", "-jar", "target/back-End-Smart-Cities-0.0.1-SNAPSHOT.jar"]
