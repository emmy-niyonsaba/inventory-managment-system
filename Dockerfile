# Step 1: Build stage
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app
COPY . .

RUN mvn clean package -DskipTests

# Step 2: Run stage
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy jar from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port (Render uses 8080 by default)
EXPOSE 8080

# Run app
ENTRYPOINT ["java", "-jar", "app.jar"]