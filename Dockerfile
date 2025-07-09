FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY target/Parcial-final-capas-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
LABEL authors="Ricardo Garcia y Marco Arana"