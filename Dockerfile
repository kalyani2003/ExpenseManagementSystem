# Use Java 17 image
FROM eclipse-temurin:17

# Set working directory
WORKDIR /app

# Copy jar file
COPY build/libs/ExpensesProject-0.0.1-SNAPSHOT.jar app.jar

# Expose application port
EXPOSE 8081

# Run application
ENTRYPOINT ["java", "-jar", "app.jar"]
