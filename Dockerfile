# Stage 1: build with Maven + JDK 21
FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /app
COPY . /app
# Build the jar (skip tests for faster builds)
RUN mvn -B clean package -DskipTests

# Stage 2: run with a small JRE 21 image
FROM eclipse-temurin:21-jre
WORKDIR /app
# copy artifact (wildcard picks the jar regardless of version)
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
