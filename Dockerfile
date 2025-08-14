# Use a base image with the Java Runtime Environment (JRE)
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from your target folder into the container
COPY target/Interview-Tracker-0.0.1-SNAPSHOT.jar app.jar

# Define the command to run your application when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]