FROM openjdk:17.0-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the jar file and dependencies into the container
COPY target/*.jar app.jar

# Make port 8080 available to the world outside the container
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java","-jar","app.jar"]