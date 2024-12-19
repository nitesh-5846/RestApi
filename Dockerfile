# Use an official OpenJDK runtime as the base image
FROM openjdk:11-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Install Maven
RUN apt-get update && apt-get install -y maven && apt-get clean

# Copy the Maven/Gradle build file and the source code to the container
COPY ./java-api/pom.xml ./
COPY ./java-api/src ./src/

# Build the app (this assumes you're using Maven; adjust for Gradle if needed)
RUN mvn clean package -DskipTests

# Copy the JAR file from the target folder to the container (adjust the path accordingly)
COPY ./java-api/target/RestApiProject-0.0.1-SNAPSHOT.jar /app/RestApiProject-0.0.1-SNAPSHOT.jar

# Expose the port that your Java app listens on (adjust port if necessary)
EXPOSE 8080

# Run the Java app (replace with your main class or JAR file)
CMD ["java", "-jar", "/app/RestApiProject-0.0.1-SNAPSHOT.jar"]
