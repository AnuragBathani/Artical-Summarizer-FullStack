# Stage 1: Build the application
# We use a Maven image with JDK 17 to build the Spring Boot application.
# The 'alpine' variant is chosen for a smaller base image size.
FROM maven:3.9.6-eclipse-temurin-17-alpine AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml file first to leverage Docker's layer caching.
# If pom.xml doesn't change, subsequent builds can reuse this layer.
COPY pom.xml .

# Download project dependencies. This step is also cached.
# We use 'dependency:go-offline' to download all dependencies without building the project yet.
# This helps in caching dependencies separately from source code changes.
RUN mvn dependency:go-offline -B

# Copy the rest of the application source code
COPY src ./src

# Build the Spring Boot application.
# '-DskipTests' is used to skip running tests during the Docker build,
# as tests should ideally be run in a separate CI/CD stage.
# 'clean package' will compile the code and package it into a JAR file.
RUN mvn clean package -DskipTests

# Stage 2: Create the final runtime image
# We use a JRE 17 image (without the full JDK) for a smaller and more secure runtime.
# 'alpine' is again chosen for its minimal size.
FROM eclipse-temurin:17-jre-alpine

# Set the working directory for the runtime environment
WORKDIR /app

# Expose the port on which the Spring Boot application will run.
# By default, Spring Boot applications run on port 8080.
EXPOSE 8080

# Copy the built JAR file from the 'builder' stage into the current stage.
# The JAR file name is constructed from the artifactId and version in your pom.xml.
# feedproproto-1.0-SNAPSHOT.jar
COPY --from=builder /app/target/feedproproto-1.0-SNAPSHOT.jar app.jar

# Define the entry point for the container.
# This command will execute the Spring Boot application when the container starts.
# '-jar' specifies that we are running an executable JAR.
ENTRYPOINT ["java", "-jar", "app.jar"]

