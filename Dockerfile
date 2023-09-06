
FROM openjdk:18-jdk as builder

# Set the working directory in the image
WORKDIR /app

# Copy the jar file into the image
COPY ./target/weather-0.0.1-SNAPSHOT.jar /app

# Run your application
CMD ["java", "-jar", "weather-0.0.1-SNAPSHOT.jar"]