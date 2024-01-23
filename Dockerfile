# Étape 1 : Construction de l'application Spring Boot
FROM maven:3.5.0-openjdk-11-slim AS builder

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src# 

RUN mvn package -DskipTests

#Étape 2 : Exécution de l'application Spring Boot dans une image JRE
FROM  openjdk:11

EXPOSE 8080
ADD /target/flyDelivery-0.0.1-SNAPSHOT.jar flyDelivery.jar

ENTRYPOINT ["java", "-jar", "flyDelivery.jar"]
